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

    public boolean makeRoom(ChatRoom chatRoom) {
        return false;
    }

    public ChatRoom findByRoomId(String roomId) {
        return dslContext.select()
                .from(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.eq(roomId))
                .fetchOneInto(ChatRoom.class);
    }

    public boolean delete(String roomId) {
        return dslContext.delete(TB_CHATROOM)
                .where(TB_CHATROOM.ROOMID.equal(roomId))
                .execute() == 1;
    }

}