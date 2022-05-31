package com.dlrtn.websocket.chat.business.chat.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class ChatPageController {

    @GetMapping("/{userId}/chats/refresh")
    public String refreshRoomList() {
        return "redirect:/api/users/{userId}/chats";
    }

}
