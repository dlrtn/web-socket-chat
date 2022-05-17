package com.dlrtn.websocket.chat.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChatMessage {

    private String chatRoomId;

    private String writer;

    private String message;

    private TrayIcon.MessageType type;

}
