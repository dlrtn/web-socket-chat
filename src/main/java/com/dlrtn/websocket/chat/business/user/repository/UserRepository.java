package com.dlrtn.websocket.chat.business.user.repository;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.jooq.Converter;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbUser.TB_USER;
import static com.dlrtn.websocket.chat.util.LocalDateTimeUtils.convertLocalDateTimeToString;


@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dslContext;

    public void save(User user) {
        dslContext.insertInto(TB_USER, TB_USER.USERNAME, TB_USER.PASSWORD, TB_USER.AUTH_ROLE, TB_USER.REAL_NAME, TB_USER.CREATED_AT, TB_USER.UPDATED_AT)
                .values(user.getUsername(), user.getPassword(), user.getAuthRole().name(), user.getRealName(), convertLocalDateTimeToString(user.getCreatedAt()), convertLocalDateTimeToString(user.getUpdatedAt()))
                .execute();
    }

    public User findByUsername(String username) {
        return dslContext.select()
                .from(TB_USER)
                .where(TB_USER.USERNAME.eq(username))
                .fetchOneInto(User.class);
    }

    public void update(User user) {
        dslContext.update(TB_USER)
                .set(TB_USER.PASSWORD, user.getPassword())
                .set(TB_USER.REAL_NAME, user.getRealName())
                .set(TB_USER.UPDATED_AT, convertLocalDateTimeToString(user.getUpdatedAt()))
                .where(TB_USER.USERNAME.eq(user.getUsername()))
                .execute();
    }

    public void delete(String username) {
        dslContext.delete(TB_USER)
                .where(TB_USER.USERNAME.eq(username))
                .execute();
    }

}
