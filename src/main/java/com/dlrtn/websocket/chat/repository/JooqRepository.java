package com.dlrtn.websocket.chat.repository;

import com.dlrtn.websocket.chat.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class JooqRepository {

    private final DSLContext dslContext;

    public User findByUsername(String username) {
        return dslContext.select()
                .from()
                .where()
                .fetchOneInto(User.class);
    }

}
