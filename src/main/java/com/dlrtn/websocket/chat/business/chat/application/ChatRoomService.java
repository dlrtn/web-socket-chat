package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMemberRole;
import com.dlrtn.websocket.chat.business.chat.model.payload.ChangeChatRoomResponse;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomResponse;
import com.dlrtn.websocket.chat.business.chat.model.payload.ExitChatRoomResponse;
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
                .chatName(request.getName())
                .chatHostUser(userId)
                .chatType(request.getChatRoomType()) //front에서 넘겨준다고 생각
                .chatPassword(roomPassword)
                .build();

        chatRoomRepository.insertChatRoom(chatRoom);
        return CreateChatRoomResponse.success();
    }

    public List<ChatRoom> getChatRooms(String userId, String roomId) {
        return Optional.ofNullable(userId)
                .map(chatRoomRepository::selectByUserId)
                .orElseThrow(() -> new CommonException(String.format("Error with userId : %s, chatId : %s", userId, roomId)));
    }

    public ChatRoom getChatRoom(String userId, String roomId) {
        return Optional.ofNullable(roomId)
                .map(chatRoomRepository::selectByChatId)
                .orElseThrow(() -> new CommonException(String.format("Error with userId : %s, chatId : %s", userId, roomId)));
    }

    public ChangeChatRoomResponse changeChatRoom(String userId, String roomId, String roomName) {
        ChatRoomMember foundChatRoomMember = chatRoomMemberRepository.selectChatRoomMemberById(userId, roomId);

        if (Objects.equals(foundChatRoomMember.getRole(), ChatRoomMemberRole.HOST) || Objects.equals(foundChatRoomMember.getRole(), ChatRoomMemberRole.ADMIN)) {
            chatRoomRepository.updateChatRoom(roomId, roomName);
            return ChangeChatRoomResponse.success();
        }

        return ChangeChatRoomResponse.failWith(String.format("Error with userId : %s, chatId : %s", userId, roomId));
    }

    public ExitChatRoomResponse exitChatRoom(String userId, String roomId) {
        ChatRoom foundChatRoom = chatRoomRepository.selectByChatId(roomId);

        if (!StringUtils.equals(foundChatRoom.getChatHostUser(), userId)) {
            return ExitChatRoomResponse.failWith(String.format("Error with userId : %s, chatId : %s", userId, roomId));
        }

        chatRoomRepository.deleteChatRoom(userId, roomId);
        return ExitChatRoomResponse.success();
    }

}
