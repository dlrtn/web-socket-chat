package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMemberRole;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomMemberRepository;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public CreateChatRoomResponse createChatRoom(String userId, CreateChatRoomRequest request) {
        List<String> memberIds = request.getChatMembers();
        memberIds.add(0, userId);

        String randomId = UUID.randomUUID().toString();
        String roomPassword = StringUtils.defaultIfEmpty(request.getChatRoomPassword(), "");

        ChatRoom chatRoom = ChatRoom.builder()
                .chatId(randomId)
                .chatName(request.getChatRoomName())
                .chatHostUser(userId)
                .chatType(request.getChatRoomType()) //front에서 넘겨준다고 생각
                .chatPassword(roomPassword)
                .build();

        chatRoomRepository.insertChatRoom(chatRoom);
        return CreateChatRoomResponse.success();
    }

    public List<ChatRoom> getChatRooms(String userId, String chatId) {
        return Optional.ofNullable(userId)
                .map(chatRoomRepository::selectByUserId)
                .orElseThrow(() -> new CommonException(String.format("Error with userId : %s", userId)));
    }

    public ChatRoom getChatRoom(String userId, String chatId) {
        return chatRoomRepository.selectByChatId(userId, chatId); //TODO 로직 수정

//        if (!chatRoomMemberRepository.existsChatRoomMember(userId, chatId)) {
//            return null;
//        }
//
//        return chatRoomRepository.selectByChatId(userId, chatId);
    }

    public ChangeChatRoomResponse changeChatRoom(String userId, String chatId, ChangeChatRoomRequest changeChatRoomRequest) {
        ChatMember foundChatMember = chatRoomMemberRepository.selectChatRoomMemberById(userId, chatId);

        if (Objects.equals(foundChatMember.getRole(), ChatMemberRole.HOST) || Objects.equals(foundChatMember.getRole(), ChatMemberRole.ADMIN)) {
            chatRoomRepository.updateChatRoom(chatId, changeChatRoomRequest.getChatName());
            return ChangeChatRoomResponse.success();
        }

        return ChangeChatRoomResponse.failWith(String.format("Error with userId : %s, chatId : %s", userId, chatId));
    }

    public ExitChatRoomResponse exitChatRoom(String userId, String chatId) {
        ChatRoom foundChatRoom = chatRoomRepository.selectByChatId(userId, chatId);

        if (!StringUtils.equals(foundChatRoom.getChatHostUser(), userId)) {
            return ExitChatRoomResponse.failWith(String.format("Error with userId : %s, chatId : %s", userId, chatId));
        }

        chatRoomRepository.deleteChatRoom(userId, chatId);
        return ExitChatRoomResponse.success();
    }

}
