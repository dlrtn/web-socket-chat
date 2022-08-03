package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.ChatState;
import com.dlrtn.websocket.chat.business.chat.model.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroom.TB_CHATROOM;
import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroomMember.TB_CHATROOM_MEMBER;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final DSLContext dslContext;

    public void insertChat(Chat chat) {
        dslContext.insertInto(TB_CHATROOM,
                        TB_CHATROOM.CHATID,
                        TB_CHATROOM.CHATNAME,
                        TB_CHATROOM.CHAT_TYPE)
                .values(chat.getChatId(),
                        chat.getChatName(),
                        chat.getChatType().name())
                .execute();
    }

    public List<ChatState> selectByUserId(String userId) {
        return dslContext.select(
                        TB_CHATROOM.CHATNAME
                )
                .from(TB_CHATROOM)
                .join(TB_CHATROOM_MEMBER)
                .on(TB_CHATROOM.CHATID.eq(TB_CHATROOM_MEMBER.CHATID))
                .where(TB_CHATROOM_MEMBER.USERID.eq(userId))
                .fetchInto(ChatState.class);
    }

    public Chat selectByChatId(String userId, String chatId) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.CHATID.eq(chatId).and(TB_CHATROOM_MEMBER.USERID.eq(userId)))
                .fetchOneInto(Chat.class);
    }

    public void updateChat(String chatId, String name) {
        dslContext.update(TB_CHATROOM)
                .set(TB_CHATROOM.CHATNAME, name)
                .where(TB_CHATROOM.CHATID.eq(chatId))
                .execute();
    }

    public void deleteChat(String userId, String chatId) {
        dslContext.delete(TB_CHATROOM_MEMBER)
                .where(TB_CHATROOM_MEMBER.CHATID.eq(chatId)
                        .and(TB_CHATROOM_MEMBER.USERID.eq(userId)))
                .execute();
    }

}
