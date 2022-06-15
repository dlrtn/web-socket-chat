package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessageType;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dlrtn.websocket.chat.business.chat.model.ChatConstants.CHAT_ROOM_PATH;

@Service
public class ChatService {

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    public List<ChatMessage> getChatMessages(String userId, String chatId, String lastMessageId, Integer size) {

    }

    public void readAllChatMessages(String userId, String chatId) {

    }

    public void sendSelfMessage(User user, ChatMessage chatMessage) {
        sendMessageToChatRoomSubscribers(user.getUsername(), chatMessage.getChatId(), chatMessage);
    }

    private MessageInformation makeMessage(UserInformation user, ChatBasicMessage message) {
        MessageDto basicMessage = makeBasicMessage(user.getId(), message);
        messageMapper.insertAndUpdateChat(basicMessage);

        List<Long> times = chatMemberMapper.selectLastConnectedAtsByChatId(message.getChatId());
        MessageInformation result = makeMessageInformation(basicMessage);
        UnreadCountCalcUtils.calcUnreadCount(result, times);

        return result;
    }

    private void sendMessageToChatRoomSubscribers(String userId, String chatId, ChatMessage chatMessage) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", ChatMessageType.USER);
        data.put("message", chatMessage);
        messageTemplate.convertAndSend(String.format(CHAT_ROOM_PATH, userId, chatId), data);
    }

}
