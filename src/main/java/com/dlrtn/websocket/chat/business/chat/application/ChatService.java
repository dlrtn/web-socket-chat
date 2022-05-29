package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.ExitRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.FindRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.MakeRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.UpdateRoomInfoRequest;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public boolean createRoom(MakeRoomRequest request) {
        String randomId = UUID.randomUUID().toString();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(request.getName())
                .build();

        return chatRoomRepository.makeRoom(chatRoom);
    }

    public List<ChatRoom> findAllRoom() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom findRoomById(FindRoomRequest request) {
        return chatRoomRepository.findByRoomId(request.getRoomId());
    }

    public boolean update(UpdateRoomInfoRequest request) {
        return chatRoomRepository.updateRoomInfo(request.getRoomId(), request.getName());
    }

    public boolean delete(ExitRoomRequest request) {
        return chatRoomRepository.delete(request.getRoomId());
    }


}
