package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.exception.AlreadyExistsUseridException;
import com.dlrtn.websocket.chat.business.user.exception.UserInfoNotMatchedException;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.business.user.repository.RedisSessionRepository;
import com.dlrtn.websocket.chat.business.user.repository.UserRepository;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import com.dlrtn.websocket.chat.config.redisdb.RedisEntity;
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
    private final RedisSessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean hasNotMatchedPassword(User user, String password) {
        return Optional.ofNullable(user)
                .map(User::getPassword)
                .filter(userPw -> passwordEncoder.matches(password, userPw))
                .isEmpty();
    }

    public User getSessionUser(String sessionId) {
        return sessionRepository
                .findById(sessionId)
                .get() //TODO NoSuchElementException 어떻게 처리할지
                .getSessionUser();
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
        if (sessionRepository.existsById(sessionId)) {
            return SignInResponse.successWith(sessionId);
        }

        User foundUser = userRepository.findByUsername(request.getUsername());
        if (hasNotMatchedPassword(foundUser, request.getPassword())) {
            throw new UserInfoNotMatchedException();
        }

        String newSessionId = UUID.randomUUID().toString();

        sessionRepository.save(
                RedisEntity.builder()
                        .sessionId(newSessionId)
                        .sessionUser(foundUser)
                        .build());
        return SignInResponse.successWith(newSessionId);
    }

    public SignOutResponse signOut(String sessionId) {
        sessionRepository.deleteById(sessionId);

        return SignOutResponse.successWith();
    }

    @Transactional
    public ChangeUserProfileResponse changeUserProfile(String sessionId, ChangeUserProfileRequest request) {
        User sessionUser = getSessionUser(sessionId);
        if (hasNotMatchedPassword(sessionUser, request.getExistingPassword())) {
            return ChangeUserProfileResponse.success();
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
                RedisEntity.builder()
                        .sessionId(sessionId)
                        .sessionUser(changedUser)
                        .build());
        return ChangeUserProfileResponse.success();
    }

    @Transactional
    public WithdrawUserResponse withdrawUser(String sessionId, WithdrawUserRequest request) {
        User sessionUser = getSessionUser(sessionId);
        if (hasNotMatchedPassword(sessionUser, request.getPassword())) {
            throw new UserInfoNotMatchedException();
        }

        userRepository.delete(sessionUser.getUsername());
        return WithdrawUserResponse.success();
    }

}
