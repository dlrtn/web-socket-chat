package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.ResponseMessage;
import com.dlrtn.websocket.chat.model.UserSessionCreation;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.*;
import com.dlrtn.websocket.chat.repository.InMemorySessionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final InMemorySessionRepository sessionRepository;

    @Transactional
    public CommonResponse signUp(SignUpRequest request) {
        User foundUser = userMapper.findByUserId(request.getUserId());
        if (Objects.nonNull(foundUser)) {
            return CommonResponse.failWith(ResponseMessage.EXISTED_USER_ID);
        }

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId(request.getUserId())
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

    public UserSessionCreation signIn(String sessionId, SignInRequest request) {
        if (sessionRepository.exists(sessionId)) {
            return UserSessionCreation.successWith(sessionId);
        }

        User foundUser = userMapper.findByUserId(request.getUserId());
        if (!validateUser(foundUser, request.getPassword())) {
            return UserSessionCreation.failWith("User id or password mismatch");
        }

        String newSessionId = UUID.randomUUID().toString();
        sessionRepository.put(newSessionId, foundUser);
        return UserSessionCreation.successWith(newSessionId);
    }

    public CommonResponse updateUserInfo(String sessionId, UserInfoUpdateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId(request.getUserId())
                .realName(request.getRealName())
                .updatedAt(now)
                .build();

        if (!sessionRepository.exists(sessionId)) {
            return CommonResponse.failWith("User is not exist or sign-in first");
        }

        userMapper.updateUserInfo(user);
        return CommonResponse.success();
    }

    public CommonResponse updatePassWord(PassWordUpdateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .userId(request.getUserId())
                .password(request.getNewPassword())
                .updatedAt(now)
                .build();

        User foundUser = userMapper.findByUserId(request.getUserId());

        if (validateUser(foundUser, request.getExistingPassword())) {
            try {
                userMapper.updatePassword(user);
                return CommonResponse.success();
            } catch (Exception e) {
                return CommonResponse.failWith(ResponseMessage.SERVER_ERROR);
            }
        }
        return CommonResponse.failWith(ResponseMessage.SERVER_ERROR);
    }

    private boolean validateUser(User user, String password) {
        return Objects.nonNull(user) && StringUtils.equals(user.getPassword(), password);
    }

    public CommonResponse deleteUser(String sessionId, DeleteUserRequest request) {
        User user = User.builder()
                .userId(request.getUserId())
                .build();

        User foundUser = userMapper.findByUserId(request.getUserId());

        if (!sessionRepository.exists(sessionId)) {
            return CommonResponse.failWith("Not Login State");
        }

        if (validateUser(foundUser, request.getPassword())) {
            try {
                userMapper.delete(user);
                return CommonResponse.success();
            } catch (Exception e) {
                return CommonResponse.failWith(ResponseMessage.SERVER_ERROR);
            }
        }
        return CommonResponse.failWith(ResponseMessage.SERVER_ERROR);

    }

}
