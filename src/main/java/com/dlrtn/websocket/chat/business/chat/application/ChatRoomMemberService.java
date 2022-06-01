package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMemberRole;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomMemberRepository;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomMemberService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public List<ChatMember> getAllChatMembers(String chatId) {
        return chatRoomMemberRepository.selectChatRoomMembers(chatId);
    }

    public CommonResponse changeChatMemberRole(String userId, String chatId, ChatMemberRole role) {
        if (chatRoomMemberRepository.existsChatRoomMember(userId, chatId)) {
            chatRoomMemberRepository.updateChatRoomMemberRole(userId, role);
            return CommonResponse.success();
        }

        return CommonResponse.failWith("Can't find user in chatRoomMemberList");
    }

}
