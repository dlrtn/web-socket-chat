package com.dlrtn.websocket.chat.repository;

import com.dlrtn.websocket.chat.model.domain.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;


@JooqTest
public class JooqRepositoryTests {

    @Autowired
    JooqRepository jooqRepository;

    @DisplayName("유저 저장 테스트")
    @Test
    void save_user_test_jooq() {

    }

    @DisplayName("유저 데이터 갱신 테스트")
    @Test
    void update_user_test_jooq() {

        User user = User.builder()
                .username("11")
                .password("11")
                .realName("11")
                .build();

        int numberUserUpdated = jooqRepository.update(user);

        User foundUser = jooqRepository.findByUsername(user.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertEquals(numberUserUpdated, 1),
                () -> Assertions.assertEquals(user.getUsername(), foundUser.getUsername()),
                () -> Assertions.assertEquals(user.getPassword(), foundUser.getPassword()),
                () -> Assertions.assertEquals(user.getRealName(), foundUser.getRealName())
        );

    }

    @DisplayName("유저 삭제 테스트")
    @Test
    void delete_user_test() {

        User user = User.builder()
                .username("11")
                .build();

        int numberUserDeleted = jooqRepository.delete(user);

        User foundUser = jooqRepository.findByUsername(user.getUsername());

        Assertions.assertAll(
                () -> Assertions.assertEquals(numberUserDeleted, 1),
                () -> Assertions.assertEquals(user.getUsername(), null),
                () -> Assertions.assertEquals(user.getPassword(), null)
        );

    }
}
