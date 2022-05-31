package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMemberRole;
import com.dlrtn.websocket.chat.business.chat.repository.ChatRoomMemberRepository;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomMemberService {

    private final ChatRoomMemberRepository chatRoomMemberRepository;

    public List<ChatRoomMember> getAllChatMembers(String chatId) {
        return chatRoomMemberRepository.selectChatRoomMember(chatId);
    }

    public CommonResponse changeChatMemberRole(String chatId, String userId, ChatRoomMemberRole role) {
        if (Objects.isNull(chatRoomMemberRepository.selectChatRoomMemberById(chatId, userId))) {
            return CommonResponse.failWith("Can't find user in chatRoomMemberList");
        }

        chatRoomMemberRepository.updateChatRoomMemberRole(userId, role);
        return CommonResponse.success();
    }


}
