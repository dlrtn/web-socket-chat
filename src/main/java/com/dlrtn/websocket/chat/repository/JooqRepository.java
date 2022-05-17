package com.dlrtn.websocket.chat.repository;

import com.dlrtn.websocket.chat.model.domain.User;

import static com.dlrtn.websocket.chat.model.domain.generated.study_db.tables.TbUser.TB_USER;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class JooqRepository {

    private final DSLContext dslContext;

    //    void save(User user) {
//        return dslContext.insertInto(TB_USER,
//                        TB_USER.USERNAME, TB_USER.PASSWORD, TB_USER.REAL_NAME, TB_USER.AUTH_ROLE, TB_USER.CREATED_AT, TB_USER.UPDATED_AT)
//                .values(user.getUsername(), user.getPassword(), user.getRealName(), user.getAuthRole(), user.getCreatedAt(), user.getUpdatedAt())
//                .excute();
//
//    }

    public User findByUsername(String username) {
        return dslContext.select()
                .from(TB_USER)
                .where(TB_USER.USERNAME.eq(username))
                .fetchOneInto(User.class);
    }


    int update(User user) {
        return dslContext.update(TB_USER)
                .set(TB_USER.PASSWORD, user.getPassword())
                .set(TB_USER.REAL_NAME, user.getRealName())
                .where(TB_USER.USERNAME.eq(user.getUsername()))
                .execute();
    }

    int delete(User user) {
        return dslContext.delete(TB_USER)
                .where(TB_USER.USERNAME.eq(user.getUsername()))
                .execute();
    }


}
