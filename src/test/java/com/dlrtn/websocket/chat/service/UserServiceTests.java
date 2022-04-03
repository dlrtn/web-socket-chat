package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.util.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Autowired
    User user;

    @Autowired
    private  UserMapper userMapper;


    @Test
    void 마이바티스_테스트() {
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = TimeUtils.formatter.format(now);

        user.toBuilder()
                .userNo(50)
                .username("dlrtn")
                .password("1234")
                .realName("wndlrtn")
                .authRole("user")
                .appendAt(formattedNow)
                .updateAt(formattedNow)
                .build();

        userMapper.saveUser(user);


    }
}
