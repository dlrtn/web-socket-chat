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
            .userId("test-user")
            .password("test-user-pw")
            .realName("test-name")
            .authRole("USER")
            .build();

    @DisplayName("유저 저장 테스트")
    @Test
    void save_user_test() {
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = TimeUtils.formatter.format(now);

        User user = TEST_USER.toBuilder()
                .userId("dlrtn")
                .password("1234")
                .realName("wndlrtn")
                .authRole("user")
                .build();

        userMapper.saveUser(user);

        User foundUser = userMapper.findUserByUserId("dlrtn");

        Assertions.assertAll("user",
                () -> Assertions.assertEquals(foundUser.getUserId(), "dlrtn"),
                () -> Assertions.assertEquals(foundUser.getPassword(), "1234"),
                () -> Assertions.assertEquals(foundUser.getRealName(), "wndlrtn"),
                () -> Assertions.assertEquals(foundUser.getAuthRole(), "user"),
                () -> Assertions.assertEquals(foundUser.getCreatedAt(), ""),
                () -> Assertions.assertEquals(foundUser.getUpdatedAt(), ""));

    }

}
