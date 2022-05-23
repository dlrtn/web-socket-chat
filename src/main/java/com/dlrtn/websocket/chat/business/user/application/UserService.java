package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import com.dlrtn.websocket.chat.business.user.repository.UserRepository;
import com.dlrtn.websocket.chat.common.model.ResponseMessage;
import com.dlrtn.websocket.chat.util.LocalDateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    private final UserRepository userRepository;

    private final InMemorySessionRepository sessionRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        User foundUser = userRepository.findByUsername(request.getUsername());

        if (Objects.nonNull(foundUser)) {
            return SignUpResponse.failWith(ResponseMessage.EXISTED_USER_ID);
        }

        LocalDateTime now = LocalDateTimeUtils.setLocalDateTime();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
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

        if (!validate(foundUser, request.getPassword())) {
            return SignInResponse.failWith("user id or password mismatch");
        }

        String newSessionId = UUID.randomUUID().toString();
        sessionRepository.put(newSessionId, foundUser);

        return SignInResponse.successWith(newSessionId);
    }

    @Transactional
    public UpdateResponse update(String sessionId, UpdateRequest request) {

        if (!sessionRepository.exists(sessionId)) {
            return UpdateResponse.failWith("please login first");
        }

        User foundUser = userRepository.findByUsername(request.getUsername());
        String newRealName = StringUtils.defaultIfEmpty(request.getNewRealName(), foundUser.getRealName());
        String newPassWord = StringUtils.defaultIfEmpty(request.getNewPassword(), foundUser.getPassword());
        User user = User.builder()
                .username(request.getUsername())
                .realName(newRealName)
                .password(newPassWord)
                .updatedAt(LocalDateTimeUtils.setLocalDateTime())
                .build();

        if (validate(foundUser, request.getExistingPassword())) {
            try {
                userRepository.update(user);
                return UpdateResponse.success();
            } catch (Exception e) {
                return UpdateResponse.failWith("password update failed");
            }
        }

        return UpdateResponse.failWith("password not correct");
    }

    private boolean validate(User user, String password) {
        return Objects.nonNull(user) && passwordEncoder.matches(password, user.getPassword());
    }

    public User find(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(username)
                .map(userRepository::findByUsername)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public DeleteResponse delete(String sessionId, DeleteRequest request) {
        User foundUser = userRepository.findByUsername(request.getUsername());

        if (Objects.isNull(foundUser) || !sessionRepository.exists(sessionId)) {
            return DeleteResponse.failWith("can't find user or not login state");
        }

        if (validate(foundUser, request.getPassword())) {
            try {
                userRepository.delete(request.getUsername());
                return DeleteResponse.success();
            } catch (Exception e) {
                return DeleteResponse.failWith(ResponseMessage.SERVER_ERROR);
            }
        }

        return DeleteResponse.failWith("password not correct");
    }

}
