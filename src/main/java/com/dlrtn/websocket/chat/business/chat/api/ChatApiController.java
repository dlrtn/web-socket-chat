package com.dlrtn.websocket.chat.business.chat.api;

import com.dlrtn.websocket.chat.business.chat.application.ChatService;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatState;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.aop.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ChatApiController {

    private final ChatService chatService;

    @GetMapping("/{userId}/chats")
    public List<ChatState> getChats(@SessionUser User sessionUser) {
        return chatService.getChats(sessionUser);
    }

    @PostMapping("/{userId}/chats")
    public CreateChatResponse createChat(@SessionUser User sessionUser,
                                             @Valid @RequestBody CreateChatRequest createChatRequest) {
        return chatService.createChat(sessionUser, createChatRequest);
    }

    @DeleteMapping("/{userId}/chats/{chatId}")
    public ExitChatResponse exitChat(@SessionUser User sessionUser,
                                         @PathVariable String chatId) {
        return chatService.exitChat(sessionUser, chatId);
    }

    @PutMapping("/{userId}/chats/{chatId}")
    public ChangeChatResponse changeChat(@SessionUser User sessionUser,
                                             @PathVariable String chatId,
                                             @Valid @RequestBody ChangeChatRequest changeChatRequest) {
        return chatService.changeChat(sessionUser, chatId, changeChatRequest);
    }

}
