package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatState;
import com.dlrtn.websocket.chat.business.chat.model.domain.Chat;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMemberRole;
import com.dlrtn.websocket.chat.business.chat.model.payload.*;
import com.dlrtn.websocket.chat.business.chat.repository.ChatMemberRepository;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRepository;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMemberRepository chatMemberRepository;

    public CreateChatResponse createChat(User sessionUser, CreateChatRequest request) {
        List<String> memberIds = request.getChatMemberIds();
        memberIds.add(0, sessionUser.getUsername());

        String randomId = UUID.randomUUID().toString();
        Chat chat = Chat.builder()
                .chatId(randomId)
                .chatName(request.getChatRoomName())
                .chatHostUser(sessionUser.getUsername())
                .chatType(request.getChatType())
                .build();

        chatRepository.insertChat(chat);
        return CreateChatResponse.success();
    }

    public List<ChatState> getChats(User sessionUser) {
        return Optional.ofNullable(sessionUser.getUsername())
                .map(chatRepository::selectByUserId)
                .orElseThrow(() -> new CommonException(String.format("Error with userId : %s", sessionUser.getUsername())));
    }

    public ChangeChatResponse changeChat(User sessionUser, String chatId, ChangeChatRequest changeChatRequest) {
        ChatMember foundChatMember = chatMemberRepository.selectChatRoomMemberById(sessionUser.getUsername(), chatId);
        if (ChatMemberRole.isUserRoleAuthorized(foundChatMember.getRole())) {
            throw new CommonException("User role is unauthorized", HttpStatus.UNAUTHORIZED);
        }
        chatRepository.updateChat(chatId, changeChatRequest.getChatName());

        return ChangeChatResponse.success();
    }

    public ExitChatResponse exitChat(User sessionUser, String chatId) {
        Chat foundChat = chatRepository.selectByChatId(sessionUser.getUsername(), chatId);
        if (!StringUtils.equals(foundChat.getChatHostUser(), sessionUser.getUsername())) {
            throw new CommonException("User role is unauthorized", HttpStatus.UNAUTHORIZED);
        }

        chatRepository.deleteChat(sessionUser.getUsername(), chatId);
        return ExitChatResponse.success();
    }

}
