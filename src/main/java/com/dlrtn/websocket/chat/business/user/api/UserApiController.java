package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.common.aop.SessionId;
import com.dlrtn.websocket.chat.common.aop.SessionUser;
import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/sign-up")
    public SignUpResponse signUpUser(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return userService.signUp(signUpRequest);
    }

    @Operation(summary = "회원 로그인")
    @PostMapping("/sign-in")
    public SignInResponse signInUser(
            @SessionId String sessionId,
            @Valid @RequestBody SignInRequest requestBody,
            HttpServletResponse response
    ) {
        SignInResponse signInResponse = userService.signIn(sessionId, requestBody);

        if (signInResponse.isSuccess()) {
            CookieUtils.createCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, signInResponse.getSessionId(), Integer.MAX_VALUE);
        }

        return signInResponse;
    }

    @Operation(summary = "회원 로그아웃")
    @PostMapping("/{userId}/sign-out")
    public SignOutResponse signOutUser(
            @SessionId String sessionId,
            HttpServletResponse response
    ) {
        SignOutResponse signOutResponse = userService.signOut(sessionId);

        if (signOutResponse.isSuccess()) {
            CookieUtils.deleteCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        }

        return signOutResponse;
    }

    @Operation(summary = "회원 정보수정")
    @PatchMapping("/{userId}/modifying")
    public ChangeUserResponse changeUser(
            @SessionId String sessionId,
            @SessionUser User sessionUser,
            @Valid @RequestBody ChangeUserRequest requestBody
    ) {
        return userService.changeUser(sessionId, sessionUser, requestBody);
    }

    @Operation(summary = "회원 정보삭제")
    @DeleteMapping("/{userId}/withdrawal")
    public WithdrawUserResponse withdrawUser(
            @SessionUser User sessionUser,
            @Valid @RequestBody WithdrawUserRequest requestBody
    ) {
        return userService.withdrawUser(sessionUser, requestBody);
    }

}
