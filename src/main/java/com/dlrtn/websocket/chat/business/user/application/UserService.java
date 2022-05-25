package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import com.dlrtn.websocket.chat.business.user.repository.UserRepository;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
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

    private final InMemorySessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        User foundUser = userRepository.findByUsername(request.getUsername());

        if (Objects.nonNull(foundUser)) {
            return SignUpResponse.failWith(ResponseMessage.EXISTED_USER_ID);
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
            userRepository.save(user); //TODO 구현 덜 끝남
            return SignUpResponse.success();
        } catch (Exception e) {
            return SignUpResponse.failWith(ResponseMessage.SERVER_ERROR);
        }
    }

    public SignInResponse signIn(String sessionId, SignInRequest request) {
        if (sessionRepository.exists(sessionId)) {
            return SignInResponse.successWith(sessionId);
        }

        User foundUser = userRepository.findByUsername(request.getUsername());

        if (hasNotMatchedPassword(foundUser, request.getPassword())) {
            return SignInResponse.failWith("user id or password mismatch");
        }

        String newSessionId = UUID.randomUUID().toString();
        sessionRepository.put(newSessionId, foundUser);

        return SignInResponse.successWith(newSessionId);
    }

    @Transactional
    public ChangeUserProfileResponse changeUserProfile(User user, ChangeUserProfileRequest request) {
        if (hasNotMatchedPassword(user, request.getExistingPassword())) {
            return ChangeUserProfileResponse.failWith("password not correct");
        }

        String newRealName = StringUtils.defaultIfEmpty(request.getNewRealName(), user.getRealName());
        String newPassword = StringUtils.defaultIfEmpty(request.getNewPassword(), user.getPassword());

        User changedUser = user.toBuilder()
                .realName(newRealName)
                .password(newPassword)
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.update(changedUser);
        return ChangeUserProfileResponse.success();
    }

    private boolean hasNotMatchedPassword(User user, String password) {
        return Optional.ofNullable(user)
                .map(User::getPassword)
                .filter(userPw -> passwordEncoder.matches(password, userPw))
                .isEmpty();
    }

    @Transactional
    public WithdrawUserResponse withdrawUser(User user, WithdrawUserRequest request) {
        if (hasNotMatchedPassword(user, request.getPassword())) {
            return WithdrawUserResponse.failWith("password not correct");
        }

        userRepository.delete(user.getUsername());
        return WithdrawUserResponse.success();
    }

}
