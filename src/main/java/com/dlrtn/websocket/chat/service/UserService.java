package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.domain.dto.SignUpRequest;
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
    public void signUp(SignUpRequest req) {
        userMapper.saveUser(joinUser(req));
    }

    public User joinUser(SignUpRequest req) {

        LocalDateTime now = LocalDateTime.now();
        String formattedNow = TimeUtils.formatter.format(now);

        return User.builder()
            .userId(req.getUserId())
            .password(req.getPassword())
            .realName(req.getRealName())
            .authRole(req.getAuthRole())
            .createdAt(formattedNow)
            .updatedAt(formattedNow)
            .build();
    }

}
