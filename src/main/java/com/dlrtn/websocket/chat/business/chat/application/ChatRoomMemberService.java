package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMemberRole;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomMemberRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomMemberService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public List<ChatMember> getAllChatMembers(String chatId) {
        return Optional.ofNullable(chatId)
                .map(chatRoomMemberRepository::selectChatRoomMembers)
                .orElseThrow(() -> new CommonException("Chatroom is empty"));
    }

    public CommonResponse changeChatMemberRole(String userId, String chatId, ChatMemberRole role) {
        if (chatRoomMemberRepository.existsChatRoomMember(userId, chatId)) {
            chatRoomMemberRepository.updateChatRoomMemberRole(userId, role);
            return CommonResponse.success();
        }

        return CommonResponse.failWith("Can't find user in chatRoomMemberList");
    }

}
