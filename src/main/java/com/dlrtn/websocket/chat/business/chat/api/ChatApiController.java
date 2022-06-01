package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.ChatRoomService;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ChatApiController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/users/{userId}/chats")
    public CreateChatRoomResponse createChatRoom(@PathVariable String userId,
                                                 @Valid @RequestBody CreateChatRoomRequest createChatRoomRequest) {
        return chatRoomService.createChatRoom(userId, createChatRoomRequest);
    }

    @GetMapping("/users/{userId}/chats/{chatId}")
    public ChatRoom findChatRoom(@PathVariable String userId,
                                 @PathVariable String chatId) {
        return chatRoomService.getChatRoom(userId, chatId);
    }

    @DeleteMapping("/users/{userId}/chats/{chatId}")
    public ExitChatRoomResponse exitChatRoom(@PathVariable String userId,
                                             @PathVariable String chatId) {
        return chatRoomService.exitChatRoom(userId, chatId);
    }

    @PutMapping("/users/{userId}/chats/{chatId}")
    public ChangeChatRoomResponse changeChatRoom(@PathVariable String userId,
                                                 @PathVariable String chatId,
                                                 @Valid @RequestBody ChangeChatRoomRequest changeChatRoomRequest) {
        return chatRoomService.changeChatRoom(userId, chatId, changeChatRoomRequest);
    }

}
