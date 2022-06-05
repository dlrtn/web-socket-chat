package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroom.TB_CHATROOM;
import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroomMember.TB_CHATROOM_MEMBER;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final DSLContext dslContext;

    public void insertChatRoom(ChatRoom chatRoom) {
        dslContext.insertInto(TB_CHATROOM,
                        TB_CHATROOM.CHATID,
                        TB_CHATROOM.CHATNAME,
                        TB_CHATROOM.CHAT_TYPE)
                .values(chatRoom.getChatId(),
                        chatRoom.getChatName(),
                        chatRoom.getChatType().name())
                .execute();
    }

    public List<ChatRoom> selectByUserId(String userId) {
        return dslContext.select(
                        TB_CHATROOM.CHATID,
                        TB_CHATROOM.CHATNAME,
                        TB_CHATROOM.CHAT_HOST,
                        TB_CHATROOM.CHAT_TYPE,
                        TB_CHATROOM.CHAT_PASSWORD
                )
                .from(TB_CHATROOM)
                .join(TB_CHATROOM_MEMBER)
                .on(TB_CHATROOM.CHATID.eq(TB_CHATROOM_MEMBER.CHATID))
                .where(TB_CHATROOM_MEMBER.USERID.eq(userId))
                .fetchInto(ChatRoom.class);
    }

    public ChatRoom selectByChatId(String userId, String chatId) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.CHATID.eq(chatId).and(TB_CHATROOM_MEMBER.USERID.eq(userId)))
                .fetchOneInto(ChatRoom.class);
    }

    public void updateChatRoom(String chatId, String name) {
        dslContext.update(TB_CHATROOM)
                .set(TB_CHATROOM.CHATNAME, name)
                .where(TB_CHATROOM.CHATID.eq(chatId))
                .execute();
    }

    public void deleteChatRoom(String userId, String chatId) {
        dslContext.delete(TB_CHATROOM_MEMBER)
                .where(TB_CHATROOM_MEMBER.CHATID.eq(chatId)
                        .and(TB_CHATROOM_MEMBER.USERID.eq(userId)))
                .execute();
    }


}
