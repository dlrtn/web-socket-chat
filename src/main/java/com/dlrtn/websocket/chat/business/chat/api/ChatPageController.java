package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.application.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.CHAT;

@RequiredArgsConstructor
@RestController
@RequestMapping(CHAT)
public class ChatPageController {

    @Autowired
    private final ChatService chatService;

    @GetMapping("/main")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    @GetMapping("/refresh")
    public String refreshRoomList() {
        return "redirect:/main";
    }

}
