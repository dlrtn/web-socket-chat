package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import com.dlrtn.websocket.chat.business.chat.application.ChatService;
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

    //TODO RequestParam, RequestBody 차이점 확인하고 적용

    @PostMapping("/main")
    public SaveRoomResponse createRoom(@Valid @RequestBody SaveRoomRequest saveRoomRequest) {
        return chatService.saveRoom(saveRoomRequest);
    }

    @PostMapping("/exitRoom")
    public ExitRoomResponse exitRoom(@Valid @RequestBody ExitRoomRequest exitRoomRequest) {
        return chatService.delete(exitRoomRequest);
    }

    @PostMapping("/updateRoom")
    public ChangeRoomInfoRespnose updateRoom(@Valid @RequestBody ChangeRoomInfoRequest changeRoomInfoRequest) {
        return chatService.changeRoomInfo(changeRoomInfoRequest);
    }

    @PostMapping("/findRoom")
    public ChatRoom findRoom(@Valid @RequestBody FindRoomRequest findRoomRequest) {
        return chatService.getRoomByName(findRoomRequest); //TODO 반환값 바꿔야할거같은데 고민해보기
    }

}
