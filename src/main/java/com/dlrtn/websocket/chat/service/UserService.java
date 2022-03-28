package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.vo.User;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Builder
@RequiredArgsConstructor
public class UserService {
    // 회원가입 시 저장시간을 넣어줄 DateTime 형
    LocalDateTime time = LocalDateTime.now();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:sss");
    String localTime = dateTimeFormatter.format(time);

    private final UserMapper userMapper;

    @Transactional
    public void joinUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        user.setUserAuth("USER");
        user.setAppendDate(localTime);
        user.setUpdateDate(localTime);



        userMapper.saveUser(user);
    }

}