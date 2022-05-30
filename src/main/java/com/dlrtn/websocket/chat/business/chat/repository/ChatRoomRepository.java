package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroom.TB_CHATROOM;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final DSLContext dslContext;

    public void insertRoom(ChatRoom chatRoom) {
        dslContext.insertInto(TB_CHATROOM, TB_CHATROOM.ROOMID, TB_CHATROOM.NAME)
                .values(chatRoom.getRoomId(), chatRoom.getName())
                .execute();
    }

    public ChatRoom selectByRoomName(String name) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.eq(name))
                .fetchOneInto(ChatRoom.class);
    }

    public List<ChatRoom> selectAllRoom() {
        return dslContext.select()
                .from(TB_CHATROOM)
                .fetchInto(ChatRoom.class);
    }

    public void updateRoom(String roomId, String name) {
        dslContext.update(TB_CHATROOM)
                .set(TB_CHATROOM.NAME, name)
                .where(TB_CHATROOM.ROOMID.eq(roomId))
                .execute();

    }

    public void deleteRoom(String roomId) {
        dslContext.delete(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.equal(roomId))
                .execute();
    }

}
