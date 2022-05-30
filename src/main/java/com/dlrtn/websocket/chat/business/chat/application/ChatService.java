package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.ChangeChatRoomRespnose;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomResponse;
import com.dlrtn.websocket.chat.business.chat.model.payload.ExitChatRoomResponse;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public CreateChatRoomResponse createChatRoom(CreateChatRoomRequest request) {
        ChatRoom foundChatRoom = chatRoomRepository.selectById(request.getName());

        if (Objects.nonNull(foundChatRoom)) {
            return CreateChatRoomResponse.failWith(ResponseMessage.EXISTED_ROOM_NAME);
        }

        String randomId = UUID.randomUUID().toString();
        String roomPassword = StringUtils.defaultIfEmpty(request.getRoomPassword(), "0000");

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(request.getName())
                .chatRoomType(request.getChatRoomType())
                .roomPassword(roomPassword)
                .build();

        chatRoomRepository.insertRoom(chatRoom);
        return CreateChatRoomResponse.success();

    }

    public ChatRoom getChatRoom(String roomId) {
        return Optional.ofNullable(roomId)
                .map(chatRoomRepository::selectById)
                .orElseThrow(() -> new CommonException("Can't find room"));
    }

    public ChangeChatRoomRespnose changeChatRoom(String roomId, String roomName) {
        ChatRoom foundChatRoom = chatRoomRepository.selectById(roomId);

        if (Objects.isNull(foundChatRoom)) {
            return ChangeChatRoomRespnose.failWith("Can't find room by name");
        }

        chatRoomRepository.updateRoom(roomId, roomName);
        return ChangeChatRoomRespnose.success();
    }

    public ExitChatRoomResponse exitChatRoom(String roomId) {
        ChatRoom foundChatRoom = chatRoomRepository.selectById(roomId);

        if (Objects.isNull(foundChatRoom)) {
            return ExitChatRoomResponse.failWith("Can't find room by name");
        }

        chatRoomRepository.deleteRoom(roomId);
        return ExitChatRoomResponse.success();
    }


}
