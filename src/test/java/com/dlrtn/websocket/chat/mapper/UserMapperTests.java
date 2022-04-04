package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.util.TimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;

    private final User TEST_USER = User.builder()
            .id("test-user")
            .password("test-user-pw")
            .realName("test-name")
            .authRole("USER")
            .appendAt("2022-01-01 00:00:00.0") // createdAt
            .updateAt("2022-01-01 00:00:00.0") // updatedAt
            .build();

    @DisplayName("유저 저장 테스트")
    @Test
    void save_user_test() {
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = TimeUtils.formatter.format(now);

        User user = TEST_USER.toBuilder()
                .id("dlrtn")
                .password("1234")
                .realName("wndlrtn")
                .authRole("user")
                .appendAt(formattedNow)
                .updateAt(formattedNow)
                .build();

        userMapper.saveUser(user);

        List<User> allUsers = userMapper.findAllUsers();

        User foundUser = userMapper.findUserById("dlrtn");

        Assertions.assertAll("user",
                () -> Assertions.assertEquals(foundUser.getId(), "dlrtn"),
                () -> Assertions.assertEquals(foundUser.getPassword(), "1234"),
                () -> Assertions.assertEquals(foundUser.getRealName(), "wndlrtn"),
                () -> Assertions.assertEquals(foundUser.getAuthRole(), "user"));


    }

}
