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
            .username("test-user")
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
                .username("dlrtn")
                .password("1234")
                .realName("wndlrtn")
                .authRole("user")
                .appendAt(formattedNow)
                .updateAt(formattedNow)
                .build();

        userMapper.saveUser(user);

        List<User> allUsers = userMapper.findAllUsers();

        User foundUser = userMapper.findUserByUsername("dlrtn");

        Assertions.assertEquals(foundUser.getUsername(), user.getUsername());
        Assertions.assertEquals(foundUser.getPassword(), user.getPassword());
        Assertions.assertEquals(foundUser.getRealName(), user.getRealName());
        Assertions.assertEquals(foundUser.getAuthRole(), user.getAuthRole());
        Assertions.assertEquals(foundUser.getAppendAt(), user.getAppendAt());
        Assertions.assertEquals(foundUser.getUpdateAt(), user.getUpdateAt());
    }

}
