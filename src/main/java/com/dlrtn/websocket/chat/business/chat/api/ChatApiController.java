package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.ChatRoomService;
import com.dlrtn.websocket.chat.business.chat.model.ChatRoomState;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ChatApiController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/{userId}/chats")
    public List<ChatRoomState> getChatRooms(@PathVariable String userId) {
        return chatRoomService.getChatRooms(userId);
    }

    @PostMapping("/{userId}/chats")
    public CreateChatRoomResponse createChatRoom(@PathVariable String userId,
                                                 @Valid @RequestBody CreateChatRoomRequest createChatRoomRequest) {
        return chatRoomService.createChatRoom(userId, createChatRoomRequest);
    }

    @GetMapping("/{userId}/chats/{chatId}")
    public ChatRoomState getChatRoom(@PathVariable String userId,
                                     @PathVariable String chatId) {
        return chatRoomService.getChatRoom(userId, chatId);
    }

    @DeleteMapping("/{userId}/chats/{chatId}")
    public ExitChatRoomResponse exitChatRoom(@PathVariable String userId,
                                             @PathVariable String chatId) {
        return chatRoomService.exitChatRoom(userId, chatId);
    }

    @PutMapping("/{userId}/chats/{chatId}")
    public ChangeChatRoomResponse changeChatRoom(@PathVariable String userId,
                                                 @PathVariable String chatId,
                                                 @Valid @RequestBody ChangeChatRoomRequest changeChatRoomRequest) {
        return chatRoomService.changeChatRoom(userId, chatId, changeChatRoomRequest);
    }

}
