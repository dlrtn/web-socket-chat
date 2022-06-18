package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.ChatJoinMessage;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessageType;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatType;
import com.dlrtn.websocket.chat.business.user.model.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.stream.Collectors;

public class WebSocketService {

    @Autowired
    private ConnectedChatListRepository chatListRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendSelfMessage(UserInformation user, ChatMessage message) {
        MessageInformation messageToSend = makeMessage(user, message);
        sendMessageToChatRoomSubscribers(message.getChatId(), messageToSend);
    }

    @Override
    public void sendPrivateMessage(UserInformation user, ChatMessage message) {
        MessageInformation messageToSend = makeMessage(user, message);
        UserActiveInformation userActive = //
                chatMemberMapper.selectTargetActiveByPrivateChatIdAndUserId(message.getChatId(), user.getUserName());

        if (userActive.getActive()) {
            sendPrivateMessageToActiveUser(message.getChatId(), userActive. (), messageToSend);
        } else {
            sendPrivateMessageToInactiveUser(message.getChatId(), userActive.getUserId(), messageToSend);
        }
    }

    @Override
    public void sendGroupJoinMessage(UserInformation user, ChatJoinMessage message) {
        String chatId = message.getChatId();
        List<String> invitedIds = message.getInvitedIds();

        MessageInformation messageToSend = makeJoinNotification(chatId, user, invitedIds);
        sendMessageToChatRoomSubscribers(chatId, messageToSend);

        ChatInformation chat = chatMapper.selectChatInformationByIdAndUserId(chatId, invitedIds.get(0));
        List<Long> connectedIds = updateChatUnreadCountOfConnectedUsers(chat, invitedIds);
        sendChatInformationToChatListSubscribers(connectedIds, chat);
    }

    @Override
    @Async
    public void sendUserExitToChats(Long userId, List<Long> exitedChatIds) {
        List<ChatRoom> exitedChats = chatMapper.selectAllByIdList(exitedChatIds);

        for (ChatRoom exitedChat : exitedChats) {
            if (exitedChat.getChatType() == ChatType.PUBLIC) {
                sendUserExitToGroupChat(exitedChat.getChatId(), userId);
            }
            sendChangedUnreadCountToMembers(exitedChat.getChatId());
        }
    }

    @Override
    @Async
    public void sendChangedUnreadCountToMembers(Long chatId) {
        List<SimpleMessageInformation> messages = readMessageRepository.getReadMessages(chatId);
        Map<String, Long> memberOffsets = readMessageRepository.getMemberOffsets(chatId);
        if (messages.isEmpty() || memberOffsets.isEmpty()) {
            return;
        }

        List<Long> times = chatMemberMapper.selectLastConnectedAtsByChatId(chatId);
        UnreadCountCalcUtils.calcUnreadCounts(messages, times);

        List<UnreadCountInformation> unreadCountInfos = getUnreadCountInformations(messages);
        List<Map.Entry<String, Long>> sortedMemberOffsets = getSortedEntryList(memberOffsets);
        sendUnreadCountToMembers(chatId, unreadCountInfos, sortedMemberOffsets);

        Long oldestMessageId = removeZeroCountReadMessages(chatId, unreadCountInfos);
        if (oldestMessageId != null) {
            updateMemberOffsets(chatId, oldestMessageId, sortedMemberOffsets);
        }
    }

    private MessageInformation makeJoinNotification(Long chatId, UserInformation user, List<Long> invitedIds) {
        List<UserInformation> members = userMapper.selectUserInformationsByIdList(invitedIds);

        String content = makeJoinMessageContent(user, members);
        MessageDto notiMessage = makeNotificationMessage(chatId, user.getId(), content);
        messageMapper.insert(notiMessage);

        return makeMessageInformation(notiMessage);
    }

    private String makeJoinMessageContent(UserInformation user, List<UserInformation> invitedUsers) {
        String invitedUsersStr = invitedUsers.stream().map(UserInformation::getName).collect(Collectors.joining(", "));

        StringBuilder result = new StringBuilder();
        result.append(user.getName() + " 님이 ");
        result.append(invitedUsersStr + " 님을 초대하였습니다.");
        return result.toString();
    }

    private MessageInformation makeExitNotification(String chatId, String userId) {
        UserInformation user = userMapper.selectUserInformationById(userId);

        String content = makeExitMessageContent(user);
        ChatMessage notiMessage = makeNotificationMessage(chatId, user.getRealName(), content);
        messageMapper.insert(notiMessage);

        return makeMessageInformation(notiMessage);
    }

    private String makeExitMessageContent(UserInformation user) {
        return user.getRealName() + " 님이 나가셨습니다.";
    }

    private ChatMessage makeNotificationMessage(String chatId, String userId, String content) {
        return ChatMessage.builder()
                .chatId(chatId)
                .sender(userId) //
                .type(ChatMessageType.NOTIFICATION).build();
    }

    private MessageInformation makeMessage(UserInformation user, ChatMessage message) {
        ChatMessage basicMessage = makeBasicMessage(user.getRealName(), message);
        messageMapper.insertAndUpdateChat(basicMessage);

        List<Long> times = chatMemberMapper.selectLastConnectedAtsByChatId(message.getChatId());
        MessageInformation result = makeMessageInformation(basicMessage);
        UnreadCountCalcUtils.calcUnreadCount(result, times);

        return result;
    }

    private MessageDto makeBasicMessage(Long userId, ChatBasicMessage message) {
        return MessageDto.builder().chatId(message.getChatId()).userId(userId) //
                .type(message.getType()).content(message.getContent()).sentAt(new Date().getTime()).build();
    }

    private MessageInformation makeMessageInformation(MessageDto message) {
        return MessageInformation.builder().id(message.getId()).type(message.getType()) //
                .userId(message.getUserId()).content(message.getContent()).sentAt(message.getSentAt()).build();
    }

    private void sendPrivateMessageToActiveUser(Long chatId, Long userId, MessageInformation message) {
        if (chatListRepository.hasUserChatUnreadCounts(userId)) {
            SimpleChatInformation chat = makeSimpleChatInformation(chatId, userId, message);
            sendSimpleChatInformationToChatListSubscriber(userId, chat);
        }

        addReadMessageHistory(chatId, message);
        sendMessageToChatRoomSubscribers(chatId, message);
    }

    private void sendPrivateMessageToInactiveUser(Long chatId, Long userId, MessageInformation message) {
        chatMemberMapper.updateActiveByChatIdAndUserId(chatId, userId, message.getSentAt());
        incrementUnreadCount(message);

        if (chatListRepository.hasUserChatUnreadCounts(userId)) {
            ChatInformation chat = chatMapper.selectChatInformationByIdAndUserId(chatId, userId);

            chatListRepository.setUserChatUnreadCount(userId, chatId, chat.getUnreadCnt());
            sendChatInformationToChatListSubscriber(userId, chat);
        }

        addReadMessageHistory(chatId, message);
        sendMessageToChatRoomSubscribers(chatId, message);
    }

    private void addReadMessageHistory(Long chatId, MessageInformation message) {
        SimpleMessageInformation simpleMsg = new SimpleMessageInformation(message);

        if (simpleMsg.getUnreadCnt() == 0) {
            readMessageRepository.deleteReadMessages(chatId);
        } else {
            readMessageRepository.rightPushReadMessage(chatId, simpleMsg);

            if (!readMessageRepository.hasOldestReadMessageId(chatId)) {
                readMessageRepository.setOldestReadMessageId(chatId, simpleMsg.getId());
                readMessageRepository.setMemberOffsets(chatId, simpleMsg.getId());
            }
        }
    }

    private void sendUserExitToGroupChat(String chatId, String userId) {
        MessageInformation message = makeExitNotification(chatId, userId);

        sendMessageToChatRoomSubscribers(chatId, message);
        sendExitUserIdToChatRoomSubscribers(chatId, message.getUserId());
    }

    private void sendMessageToChatRoomSubscribers(String chatId, MessageInformation message) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", WebSocketMessageType.NEWMESSAGE);
        data.put("message", message);
        messagingTemplate.convertAndSend(CHAT_ROOM_DESTINATION_PREFIX + chatId, data);
    }

    private List<SimpleChatInformation> makeSimpleChatInformations(String chatId, List<String> userIds,
                                                                   MessageInformation message) {
        List<SimpleChatInformation> chats = new ArrayList<SimpleChatInformation>();
        for (String userId : userIds) {
            chats.add(makeSimpleChatInformation(chatId, userId, message));
        }
        return chats;
    }

    private SimpleChatInformation makeSimpleChatInformation(String chatId, String userId, MessageInformation message) {
        int unreadCount = chatListRepository.incrementUserChatUnreadCount(userId, chatId);
        return SimpleChatInformation.builder()
                .id(chatId)
                .lastMessage(message.getContent())
                .lastAt(message.getSentAt())
                .unreadCnt(unreadCount).build();
    }

    private void sendChatInformationToChatListSubscribers(List<String> userIds, ChatInformation chat) {
        for (String userId : userIds) {
            sendChatInformationToChatListSubscriber(userId, chat);
        }
    }

    private void sendChatInformationToChatListSubscriber(String userId, ChatInformation chat) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", ChatStatusType.NEW);
        data.put("chat", chat);
        messagingTemplate.convertAndSend(CHAT_LIST_DESTINATION_PREFIX + userId, data);
    }

    private void sendSimpleChatInformationToChatListSubscribers(List<Long> userIds, List<SimpleChatInformation> chats) {
        for (int i = 0; i < userIds.size(); i++) {
            Long userId = userIds.get(i);
            SimpleChatInformation chat = chats.get(i);
            sendSimpleChatInformationToChatListSubscriber(userId, chat);
        }
    }

    private void sendSimpleChatInformationToChatListSubscriber(Long userId, SimpleChatInformation chat) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", ChatStatusType.EXIST);
        data.put("chat", chat);
        messagingTemplate.convertAndSend(CHAT_LIST_DESTINATION_PREFIX + userId, data);
    }

    private void sendExitUserIdToChatRoomSubscribers(Long chatId, Long userId) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", WebSocketMessageType.EXIT);
        data.put("userId", userId);
        messagingTemplate.convertAndSend(CHAT_ROOM_DESTINATION_PREFIX + chatId, data);
    }

    private void updateMemberOffsets(Long chatId, Long oldestMessageId, List<Map.Entry<String, Long>> sortedMemberOffsets) {
        for (Map.Entry<String, Long> entry : sortedMemberOffsets) {
            if (entry.getValue() < oldestMessageId) {
                readMessageRepository.setMemberOffset(chatId, Long.parseLong(entry.getKey()), oldestMessageId);
            } else {
                return;
            }
        }
    }

}
