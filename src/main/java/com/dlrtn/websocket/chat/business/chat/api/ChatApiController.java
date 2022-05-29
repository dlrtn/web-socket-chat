package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.ExitRoomRequest;
import com.dlrtn.websocket.chat.business.chat.application.ChatService;
import com.dlrtn.websocket.chat.business.chat.model.payload.FindRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.MakeRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.UpdateRoomInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.CHAT;

@RequiredArgsConstructor
@RestController
@RequestMapping(CHAT)
public class ChatApiController {

    @Autowired
    private final ChatService chatService;

    //TODO ReuqestParam, RequestBody 차이점 확인하고 적용

    @PostMapping
    public boolean createRoom(@Valid @RequestBody MakeRoomRequest makeRoomRequest) {
        return chatService.createRoom(makeRoomRequest);
    }

    @PostMapping("/delete")
    public boolean exitRoom(@Valid @RequestBody ExitRoomRequest exitRoomRequest) {
        return chatService.delete(exitRoomRequest);
    }

    @PostMapping("/update")
    public boolean updateRoomInfo(@Valid @RequestBody UpdateRoomInfoRequest updateRoomInfoRequest) {
        return chatService.update(updateRoomInfoRequest);
    }

    @PostMapping("/find")
    public ChatRoom findRoom(@Valid @RequestBody FindRoomRequest findRoomRequest) {
        return chatService.findRoomById(findRoomRequest); //TODO 반환값 바꿔야할거같은데 고민해보기
    }

}
