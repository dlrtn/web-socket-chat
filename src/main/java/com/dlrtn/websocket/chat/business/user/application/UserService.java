package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.exception.AlreadyExistsUseridException;
import com.dlrtn.websocket.chat.business.user.exception.FailedToSignOutException;
import com.dlrtn.websocket.chat.business.user.exception.FailedToUserAuthenticationException;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.business.user.repository.UserRepository;
import com.dlrtn.websocket.chat.business.user.repository.UserSessionRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.config.redisdb.SessionEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    private final UserRepository userRepository;
    private final UserSessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean validatePassword(User user, String password) {
        return Optional.ofNullable(user)
                .map(User::getPassword)
                .filter(userPw -> passwordEncoder.matches(password, userPw))
                .isEmpty();
    }

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        User foundUser = userRepository.findByUsername(request.getUsername());
        if (Objects.nonNull(foundUser)) {
            throw new AlreadyExistsUseridException();
        }

        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .realName(request.getRealName())
                .authRole(request.getAuthRole())
                .createdAt(now)
                .updatedAt(now)
                .build();

        try {
            userRepository.save(user);
            return SignUpResponse.success();
        } catch (Exception e) {
            throw new CommonException(e);
        }
    }

    public SignInResponse signIn(String sessionId, SignInRequest request) {
        if (Objects.nonNull(sessionId) && sessionRepository.existsById(sessionId)) {
            return SignInResponse.successWith(sessionId);
        }

        User foundUser = userRepository.findByUsername(request.getUsername());
        if (validatePassword(foundUser, request.getPassword())) {
            throw new FailedToUserAuthenticationException();
        }

        String newSessionId = UUID.randomUUID().toString();
        sessionRepository.save(
                SessionEntity.builder()
                        .sessionId(newSessionId)
                        .sessionUser(foundUser)
                        .build());

        return SignInResponse.successWith(newSessionId);
    }

    public SignOutResponse signOut(String sessionId) {
        try {
            sessionRepository.deleteById(sessionId);
            return SignOutResponse.success();
        } catch (Exception e) {
            throw new FailedToSignOutException();
        }

    }

    @Transactional
    public ChangeUserResponse changeUser(String sessionId, User sessionUser, ChangeUserRequest request) {
        if (validatePassword(sessionUser, request.getExistingPassword())) {
            return ChangeUserResponse.success();
        }

        String newRealName = StringUtils.defaultIfEmpty(request.getNewRealName(), sessionUser.getRealName());
        String newPassword = StringUtils.defaultIfEmpty(request.getNewPassword(), sessionUser.getPassword());

        User changedUser = sessionUser.toBuilder()
                .realName(newRealName)
                .password(passwordEncoder.encode(newPassword))
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.update(changedUser);

        sessionRepository.save(
                SessionEntity.builder()
                        .sessionId(sessionId)
                        .sessionUser(changedUser)
                        .build());

        return ChangeUserResponse.success();
    }

    @Transactional
    public WithdrawUserResponse withdrawUser(User sessionUser, WithdrawUserRequest request) {
        if (validatePassword(sessionUser, request.getPassword())) {
            throw new FailedToUserAuthenticationException();
        }
        userRepository.delete(sessionUser.getUsername());

        return WithdrawUserResponse.success();
    }

}
