package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.config.LightMyBatisTest;
import com.dlrtn.websocket.chat.model.UserAuthRole;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.CommonResponse;
import com.dlrtn.websocket.chat.model.payload.DeleteUserRequest;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@LightMyBatisTest
public class UserMapperTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    @DisplayName("유저 저장 테스트")
    @Test
    void save_user_test() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime filteredNow = now.truncatedTo(ChronoUnit.SECONDS);

        User user = User.builder()
                .userId("1")
                .password("1")
                .realName("1234")
                .authRole(UserAuthRole.USER)
                .createdAt(now)
                .updatedAt(now)
                .build();

        userMapper.save(user);
        User foundUser = userMapper.findByUserId("1");

        Assertions.assertAll(
                () -> Assertions.assertEquals("1", foundUser.getUserId()),
                () -> Assertions.assertEquals("1", foundUser.getPassword()),
                () -> Assertions.assertEquals("1234", foundUser.getRealName()),
                () -> Assertions.assertEquals(UserAuthRole.USER, foundUser.getAuthRole()),
                () -> Assertions.assertEquals(filteredNow, foundUser.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)),
                () -> Assertions.assertEquals(filteredNow, foundUser.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS))
        );
    }

    @DisplayName("유저 수정 테스트")
    @Test
    void update_user_test() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS); //이게 맞나..

        User user = User.builder()
                .userId("1")
                .password("1")
                .realName("1234")
                .updatedAt(now)
                .build();

        userMapper.update(user);

        User foundUser = userMapper.findByUserId("1");

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getUserId(), foundUser.getUserId()),
                () -> Assertions.assertEquals(user.getPassword(), foundUser.getUserId()),
                () -> Assertions.assertEquals(user.getRealName(), foundUser.getRealName()),
                () -> Assertions.assertEquals(user.getUpdatedAt(), foundUser.getUpdatedAt())
        );

    }

    @DisplayName("유저 삭제 테스트")
    @Test
    void delete_user_test() {

        User user = User.builder()
                .userId("1")
                .build();

        userMapper.delete(user);

        User foundUser = userMapper.findByUserId("1");

        Assertions.assertEquals(null, foundUser);

    }

}
