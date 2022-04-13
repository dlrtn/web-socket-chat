package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.config.LightMyBatisTest;
import com.dlrtn.websocket.chat.model.domain.User;
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

        User user = User.builder()
                .userId("dlrtn")
                .password("1234")
                .realName("wndlrtn")
                .authRole("user")
                .createdAt(now)
                .updatedAt(now)
                .build();

        userMapper.save(user);

        User foundUser = userMapper.findByUserId("dlrtn");

        LocalDateTime filteredNow = now.truncatedTo(ChronoUnit.SECONDS);

        Assertions.assertAll(
                () -> Assertions.assertEquals("dlrtn", foundUser.getUserId()),
                () -> Assertions.assertEquals("1234", foundUser.getPassword()),
                () -> Assertions.assertEquals("wndlrtn", foundUser.getRealName()),
                () -> Assertions.assertEquals("user", foundUser.getAuthRole()),
                () -> Assertions.assertEquals(filteredNow, foundUser.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)),
                () -> Assertions.assertEquals(filteredNow, foundUser.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS))
        );
    }

}
