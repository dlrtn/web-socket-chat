package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMember;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMemberRole;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.Tables.TB_CHAT_MEMBER;

@Repository
@RequiredArgsConstructor
public class ChatMemberRepository {

    private final DSLContext dslContext;

    public List<ChatMember> selectChatMembers(String chatId) {
        return dslContext.select()
                .from(TB_CHAT_MEMBER)
                .where(TB_CHAT_MEMBER.CHATID.eq(chatId))
                .fetchInto(ChatMember.class);
    }

    public void updateChatMemberRole(String userId, ChatMemberRole role) {
        dslContext.update(TB_CHAT_MEMBER)
                .set(TB_CHAT_MEMBER.ROLE, role.name())
                .where(TB_CHAT_MEMBER.USERID.eq(userId))
                .execute();
    }

    public ChatMember selectChatRoomMemberById(String userId, String chatId) {
        return dslContext.select()
                .from(TB_CHAT_MEMBER)
                .where(TB_CHAT_MEMBER.USERID.eq(userId).and(TB_CHAT_MEMBER.CHATID.eq(chatId)))
                .fetchOneInto(ChatMember.class);
    }

    public boolean existsChatMember(String userId, String chatId) {
        return dslContext.select()
                .from(TB_CHAT_MEMBER)
                .where(TB_CHAT_MEMBER.CHATID.eq(chatId).and(TB_CHAT_MEMBER.USERID.eq(userId)))
                .execute() == 1;
    }

}
