package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMemberRole;
import com.dlrtn.websocket.chat.business.chat.repository.ChatMemberRepository;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMemberService {

    private final ChatMemberRepository chatMemberRepository;

    public List<ChatMember> getChatMembers(String chatId) {
        return chatMemberRepository.selectChatMembers(chatId);
    }

    public CommonResponse changeChatMemberRole(String userId, String chatId, ChatMemberRole role) {
        if (chatMemberRepository.existsChatMember(userId, chatId)) {
            chatMemberRepository.updateChatMemberRole(userId, role);
            return CommonResponse.success();
        }

        return CommonResponse.failWith("Can't find user in chatRoomMemberList");
    }

}
