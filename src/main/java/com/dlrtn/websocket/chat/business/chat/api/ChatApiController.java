package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.ChatRoomService;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatApiController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/users/{userId}/chats")
    public List<ChatRoom> getChatRooms(@PathVariable String userId) {
        return chatRoomService.getChatRooms(userId);
    }

    @PostMapping("/users/{userId}/chats")
    public CreateChatRoomResponse createChatRoom(@PathVariable String userId,
                                                 @Valid @RequestBody CreateChatRoomRequest createChatRoomRequest) {
        return chatRoomService.createChatRoom(userId, createChatRoomRequest);
    }

    @GetMapping("/users/{userId}/chats/{chatId}")
    public ChatRoom getChatRoom(@PathVariable String userId,
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
