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

    public CommonResponse update(String sessionId, UserInfoUpdateRequest request) {
        if (!sessionRepository.exists(sessionId)) {
            return CommonResponse.failWith("please login frist");
        }

        LocalDateTime now = LocalDateTime.now();

        User foundUser = userMapper.findByUserId(request.getUserId());

        String newRealName = foundUser.getRealName();
        String newPassWord = foundUser.getPassword();

        if (request.getNewPassword() != "" && request.getNewrealName() != "") { // 둘다 변경하고자 값을 삽입한 경우..
            newRealName = request.getNewrealName();
            newPassWord = request.getNewPassword();
        }
        else if (request.getNewPassword() != "") { // 패스워드 변경만 원할 경우,
            newPassWord = request.getNewPassword();
        }
        else if (request.getNewrealName() != "") { // 실명 변경만 원할 경우,
            newRealName = request.getNewrealName();
        }

        User user = User.builder()
                .userId(request.getUserId())
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
        return Objects.nonNull(user) && StringUtils.equals(user.getPassword(), password);
    }

    public CommonResponse deleteUser(String sessionId, DeleteUserRequest request) {
        User user = User.builder()
                .userId(request.getUserId())
                .build();

        User foundUser = userMapper.findByUserId(request.getUserId());

        if (!sessionRepository.exists(sessionId)) {
            return CommonResponse.failWith("Not Login State, please sign-in first");
        }

        if (validateUser(foundUser, request.getPassword())) {
            try {
                userMapper.delete(user);
                return CommonResponse.success();
            } catch (Exception e) {
                return CommonResponse.failWith(ResponseMessage.SERVER_ERROR); // 유저 삭제에 실패.. 흠
            }
        }
        return CommonResponse.failWith("Password not correct");

    }

}
