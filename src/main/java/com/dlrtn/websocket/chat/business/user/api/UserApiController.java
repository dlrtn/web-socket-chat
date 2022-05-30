package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.business.user.aop.SessionId;
import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.API;
import static com.dlrtn.websocket.chat.common.model.PagePathConstants.USER;

@Slf4j
@RestController
@RequestMapping(API + USER)
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
            CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, signInResponse.getSessionId());
        }

        return signInResponse;
    }

    @Operation(summary = "회원 정보수정")
    @PatchMapping("/{userId}/modifying")
    public ChangeUserProfileResponse changeUser(
            @SessionId String sessionId,
            @Valid @RequestBody ChangeUserProfileRequest requestBody
    ) {
        return userService.changeUserProfile(sessionId, requestBody);
    }

    @Operation(summary = "회원 정보 삭제")
    @DeleteMapping("/{userId}/withdrawal")
    public WithdrawUserResponse withdrawUser(
            @SessionId String sessionId,
            @Valid @RequestBody WithdrawUserRequest requestBody
    ) {
        return userService.withdrawUser(sessionId, requestBody);
    }

}
