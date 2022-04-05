package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    @Transactional
    public void joinUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = TimeUtils.formatter.format(now);

        User createdUser = user.toBuilder()
                .appendAt(formattedNow)
                .updateAt(formattedNow)
                .build();

        userMapper.saveUser(createdUser);
    }
}