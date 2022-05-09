package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.ResponseMessage;
import com.dlrtn.websocket.chat.model.UserSessionCreation;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.*;
import com.dlrtn.websocket.chat.repository.InMemorySessionRepository;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final InMemorySessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public CommonResponse signUp(SignUpRequest request) {
        User foundUser = userMapper.findByUsername(request.getUsername());
        if (Objects.nonNull(foundUser)) {
            return CommonResponse.failWith(ResponseMessage.EXISTED_USER_ID);
        }

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .realName(request.getRealName())
                .authRole(request.getAuthRole())
                .createdAt(now)
                .updatedAt(now)
                .build();

        try {
            userMapper.save(user);
            return CommonResponse.success();
        } catch (Exception e) {
            // TODO 로깅 추가
            return CommonResponse.failWith(ResponseMessage.SERVER_ERROR);
        }
    }

    @Transactional
    public void encryptPassword(String password){
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.builder()
                .password(encodedPassword)
                .build();
        userMapper.save(user);
    }

    public UserSessionCreation signIn(String sessionId, SignInRequest request) {
        if (sessionRepository.exists(sessionId)) {
            return UserSessionCreation.successWith(sessionId);
        }

        User foundUser = userMapper.findByUsername(request.getUsername());
        if (!validateUser(foundUser, request.getPassword())) {
            return UserSessionCreation.failWith("User id or password mismatch");
        }

        String newSessionId = UUID.randomUUID().toString();
        sessionRepository.put(newSessionId, foundUser);
        return UserSessionCreation.successWith(newSessionId);
    }

    @Transactional
    public CommonResponse update(String sessionId, UserInfoUpdateRequest request) {
        if (!sessionRepository.exists(sessionId)) {
            return CommonResponse.failWith("please login first");
        }

        LocalDateTime now = LocalDateTime.now();

        User foundUser = userMapper.findByUsername(request.getUsername());

        String newRealName = StringUtils.defaultIfEmpty(request.getNewRealName(), foundUser.getRealName());
        String newPassWord = StringUtils.defaultIfEmpty(request.getNewPassword(), foundUser.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .realName(newRealName)
                .password(newPassWord)
                .updatedAt(now)
                .build();

        if (validateUser(foundUser, request.getExistingPassword())) {
            try {
                userMapper.update(user);
                return CommonResponse.success();
            } catch (Exception e) {
                return CommonResponse.failWith("Password update failed");
            }
        }
        return CommonResponse.failWith("Password not correct");
    }

    private boolean validateUser(User user, String password) {
        return Objects.nonNull(user) && passwordEncoder.matches(user.getPassword(), password);
    }

    public User findOne(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(username)
                .map(userMapper::findByUsername)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public CommonResponse deleteUser(String sessionId, DeleteUserRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .build();

        User foundUser = userMapper.findByUsername(request.getUsername());

        if (Objects.isNull(foundUser) || !sessionRepository.exists(sessionId)) {
            return CommonResponse.failWith("Can't Find User or Not Login State");
        }

        if (validateUser(foundUser, request.getPassword())) {
            try {
                userMapper.delete(request.getUsername());
                return CommonResponse.success();
            } catch (Exception e) {
                return CommonResponse.failWith(ResponseMessage.SERVER_ERROR);
            }
        }
        return CommonResponse.failWith("Password not correct");

    }


}
