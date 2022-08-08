package com.dlrtn.websocket.chat.business.chat.repository;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.Tables.TB_CHAT_MESSAGE;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {

    private final DSLContext dslContext;

    public void insertChatMessage(ChatMessage message) {
        dslContext.insertInto(TB_CHAT_MESSAGE,
                        TB_CHAT_MESSAGE.CHAT_ID,
                        TB_CHAT_MESSAGE.MESSAGE,
                        TB_CHAT_MESSAGE.SENDER_ID
                )
                .values(message.getChatId(),
                        message.getMessage(),
                        message.getSender())
                .execute();
    }

}
