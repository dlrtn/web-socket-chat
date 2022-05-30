package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbChatroom.TB_CHATROOM;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {

    private final DSLContext dslContext;

    public void insertRoom(ChatRoom chatRoom) {
        dslContext.insertInto(TB_CHATROOM,
                        TB_CHATROOM.ROOMID,
                        TB_CHATROOM.NAME,
                        TB_CHATROOM.CHATROOM_TYPE,
                        TB_CHATROOM.ROOM_PASSWORD)
                .values(chatRoom.getRoomId(),
                        chatRoom.getName(),
                        chatRoom.getChatRoomType().name(),
                        chatRoom.getRoomPassword())
                .execute();
    }

    public ChatRoom selectById(String roomId) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.eq(roomId))
                .fetchOneInto(ChatRoom.class);
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
