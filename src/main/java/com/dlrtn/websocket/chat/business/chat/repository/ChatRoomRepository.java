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

    public boolean makeRoom(ChatRoom chatRoom) {
        return dslContext.insertInto(TB_CHATROOM, TB_CHATROOM.ROOMID, TB_CHATROOM.NAME)
                .values(chatRoom.getRoomId(), chatRoom.getName())
                .execute() == 1;
    }

    public ChatRoom findByRoomId(String roomId) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.eq(roomId))
                .fetchOneInto(ChatRoom.class);
    }

    public List<ChatRoom> findAll() {
        return dslContext.select()
                .from(TB_CHATROOM)
                .fetchInto(ChatRoom.class);
    }

    public boolean updateRoomInfo(String roomId, String name) {
        return dslContext.update(TB_CHATROOM)
                .set(TB_CHATROOM.NAME, name)
                .where(TB_CHATROOM.ROOMID.eq(roomId))
                .execute() == 1; //TODO 과연 좋은 방법인가..

    }

    public boolean delete(String roomId) {
        return dslContext.delete(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.equal(roomId))
                .execute() == 1;
    }



}