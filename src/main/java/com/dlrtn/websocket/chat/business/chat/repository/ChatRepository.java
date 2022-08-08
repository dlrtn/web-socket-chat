package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.Chat;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatState;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.Tables.TB_CHAT;
import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.Tables.TB_CHAT_MEMBER;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final DSLContext dslContext;

    public void insertChat(Chat chat) {
        dslContext.insertInto(TB_CHAT,
                        TB_CHAT.CHAT_ID,
                        TB_CHAT.CHAT_NAME,
                        TB_CHAT.CHAT_TYPE)
                .values(chat.getChatId(),
                        chat.getChatName(),
                        chat.getChatType().name())
                .execute();
    }

    public List<ChatState> selectByUserId(String userId) {
        return dslContext.select(
                        TB_CHAT.CHAT_NAME
                )
                .from(TB_CHAT)
                .join(TB_CHAT_MEMBER)
                .on(TB_CHAT.CHAT_ID.eq(TB_CHAT_MEMBER.CHATID))
                .where(TB_CHAT_MEMBER.USERID.eq(userId))
                .fetchInto(ChatState.class);
    }

    public Chat selectByChatId(String userId, String chatId) {
        return dslContext.select()
                .from(TB_CHAT)
                .where(TB_CHAT.CHAT_ID.eq(chatId).and(TB_CHAT_MEMBER.USERID.eq(userId)))
                .fetchOneInto(Chat.class);
    }

    public void updateChat(String chatId, String name) {
        dslContext.update(TB_CHAT)
                .set(TB_CHAT.CHAT_NAME, name)
                .where(TB_CHAT.CHAT_ID.eq(chatId))
                .execute();
    }

    public void deleteChat(String userId, String chatId) {
        dslContext.delete(TB_CHAT_MEMBER)
                .where(TB_CHAT_MEMBER.CHATID.eq(chatId)
                        .and(TB_CHAT_MEMBER.USERID.eq(userId)))
                .execute();
    }

}
