package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoomMember;
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
                        TB_CHATROOM.NAME,
                        TB_CHATROOM.CHATROOM_TYPE,
                        TB_CHATROOM.ROOM_PASSWORD)
                .values(chatRoom.getChatId(),
                        chatRoom.getName(),
                        chatRoom.getChatRoomType().name(),
                        chatRoom.getPassword())
                .execute();
    }

    public ChatRoom selectById(String chatId) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.CHATID.eq(chatId))
                .fetchOneInto(ChatRoom.class);
    }

    public void updateChatRoom(String chatId, String name) {
        dslContext.update(TB_CHATROOM)
                .set(TB_CHATROOM.NAME, name)
                .where(TB_CHATROOM.CHATID.eq(chatId))
                .execute();
    }

    public void deleteChatRoom(String chatId) {
        dslContext.delete(TB_CHATROOM)
                .where(TB_CHATROOM.CHATID.equal(chatId))
                .execute();
    }

}
