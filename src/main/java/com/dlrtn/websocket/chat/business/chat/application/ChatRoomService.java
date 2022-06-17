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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public CreateChatRoomResponse createChatRoom(String userId, CreateChatRoomRequest request) {
        List<String> memberIds = request.getChatMembers();
        memberIds.add(0, userId);

        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .chatId(randomId)
                .chatName(request.getChatRoomName())
                .chatHostUser(userId)
                .chatType(request.getChatType())
                .build();

        chatRoomRepository.insertChatRoom(chatRoom);
        return CreateChatRoomResponse.success();
    }

    public List<ChatRoom> getChatRooms(String userId) {
        return Optional.ofNullable(userId)
                .map(chatRoomRepository::selectByUserId)
                .orElseThrow(() -> new CommonException(String.format("Error with userId : %s", userId)));
    }

    public ChatRoom getChatRoom(String userId, String chatId) {
        return chatRoomRepository.selectByChatId(userId, chatId);
    }

    public ChangeChatRoomResponse changeChatRoom(String userId, String chatId, ChangeChatRoomRequest changeChatRoomRequest) {
        ChatMember foundChatMember = chatRoomMemberRepository.selectChatRoomMemberById(userId, chatId);

        if (ChatMemberRole.isUserRoleAuthorized(foundChatMember.getRole())) {
            throw new CommonException("User role is unauthorized", HttpStatus.UNAUTHORIZED);
        }
        chatRoomRepository.updateChatRoom(chatId, changeChatRoomRequest.getChatName());

        return ChangeChatRoomResponse.success();
    }

    public ExitChatRoomResponse exitChatRoom(String userId, String chatId) {
        ChatRoom foundChatRoom = chatRoomRepository.selectByChatId(userId, chatId);
        if (!StringUtils.equals(foundChatRoom.getChatHostUser(), userId)) {
            throw new CommonException("User role is unauthorized", HttpStatus.UNAUTHORIZED);
        }

        chatRoomRepository.deleteChatRoom(userId, chatId);
        return ExitChatRoomResponse.success();
    }

}
