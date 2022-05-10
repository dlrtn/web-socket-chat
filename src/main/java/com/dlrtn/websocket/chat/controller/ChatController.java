package com.dlrtn.websocket.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dlrtn.websocket.chat.model.PagePathConstants.*;

@Controller
@RequestMapping(CHAT)
@RequiredArgsConstructor
public class ChatController {

    @Operation(summary = "채팅 내용 수신!!")
    @GetMapping(MAIN)
    public String chatGET() {
        return MAIN;
    }

}
