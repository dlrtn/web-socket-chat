package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMemberRole;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroomMember.TB_CHATROOM_MEMBER;

@Repository
@RequiredArgsConstructor
public class ChatRoomMemberRepository {

    private final DSLContext dslContext;

    public List<ChatRoomMember> selectChatRoomMember(String chatId) {
        return dslContext.select()
                .from(TB_CHATROOM_MEMBER)
                .where(TB_CHATROOM_MEMBER.CHATID.eq(chatId))
                .fetchInto(ChatRoomMember.class);
    }

    public void updateChatRoomMemberRole(String userId, ChatRoomMemberRole role) {
        dslContext.update(TB_CHATROOM_MEMBER)
                .set(TB_CHATROOM_MEMBER.ROLE, role.name())
                .where(TB_CHATROOM_MEMBER.USERID.eq(userId))
                .execute();
    }

    public ChatRoomMember selectChatRoomMemberById(String userId, String chatId) {
        return dslContext.select()
                .from(TB_CHATROOM_MEMBER)
                .where(TB_CHATROOM_MEMBER.USERID.eq(userId).and(TB_CHATROOM_MEMBER.CHATID.eq(chatId)))
                .fetchOneInto(ChatRoomMember.class);
    }

}
