package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomRepository;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public SaveRoomResponse saveRoom(SaveRoomRequest request) {
        ChatRoom foundChatRoom = chatRoomRepository.selectByRoomName(request.getName());

        if (Objects.nonNull(foundChatRoom)) {
            return SaveRoomResponse.failWith(ResponseMessage.EXISTED_ROOM_NAME);
        }

        String randomId = UUID.randomUUID().toString();
        String roomPassword = StringUtils.defaultIfEmpty(request.getRoomPassword(), "0000");

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(request.getName())
                .chatRoomType(request.getChatRoomType())
                .roomPassword(roomPassword)
                .build();

        try {
            chatRoomRepository.insertRoom(chatRoom);
            return SaveRoomResponse.success();
        } catch (Exception e) {
            return SaveRoomResponse.failWith(ResponseMessage.SERVER_ERROR);
        }

    }

    public List<ChatRoom> getAllRoom() {
        List<ChatRoom> chatRoomList = chatRoomRepository.selectAllRoom();

        if (Objects.isNull(chatRoomList)) {
            return null;
        }

        return chatRoomList;
    }

    public ChatRoom getRoomByName(FindRoomRequest request) {
        return Optional.ofNullable(request.getName())
                .map(chatRoomRepository::selectByRoomName)
                .orElseThrow(null);
    }

    public ChangeRoomInfoRespnose changeRoomInfo(ChangeRoomInfoRequest request) {
        ChatRoom foundChatRoom = chatRoomRepository.selectByRoomName(request.getName());

        if (Objects.isNull(foundChatRoom)) {
            return ChangeRoomInfoRespnose.failWith("Can't find room by name");
        }

        chatRoomRepository.updateRoom(request.getRoomId(), request.getName());
        return ChangeRoomInfoRespnose.success();
    }

    public ExitRoomResponse delete(ExitRoomRequest request) {
        ChatRoom foundChatRoom = chatRoomRepository.selectByRoomName(request.getName());

        if (Objects.isNull(foundChatRoom)) {
            return ExitRoomResponse.failWith("Can't find room by name");
        }
        chatRoomRepository.deleteRoom(request.getRoomId());
        return ExitRoomResponse.success();
    }


}
