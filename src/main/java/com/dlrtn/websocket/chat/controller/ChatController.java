package com.dlrtn.websocket.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @Operation(summary = "채팅 내용 수신!!")
    @GetMapping("/chat")
    public String chatGET() {
        return "chat";
    }

}
