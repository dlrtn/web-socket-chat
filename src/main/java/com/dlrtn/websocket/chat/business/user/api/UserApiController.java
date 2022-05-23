package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.*;

@Slf4j
@RestController
@RequestMapping(API + USER)
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping(SIGNUP)
    public SignUpResponse signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return userService.signUp(signUpRequest);
    }

    @Operation(summary = "회원 로그인")
    @PostMapping(LOGIN)
    public SignInResponse signIn(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody SignInRequest requestBody
    ) {
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        SignInResponse signInResponse = userService.signIn(sessionId, requestBody);

        if (signInResponse.isSuccess()) {
            CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, signInResponse.getSessionId());
            return SignInResponse.successWith(sessionId);
        } else {
            return SignInResponse.failWith("error");
        }
    }

    @Operation(summary = "회원 정보수정")
    @PatchMapping(MODIFYING)
    public UpdateResponse update(
            HttpServletRequest request,
            @Valid @RequestBody UpdateRequest requestBody
    ) {
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        return userService.update(sessionId, requestBody);
    }

    @Operation(summary = "회원 정보 삭제")
    @DeleteMapping(WITHDRAWAL)
    public DeleteResponse deleteUser(
            HttpServletRequest request,
            @Valid @RequestBody DeleteRequest requestBody
    ) {
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        return userService.delete(sessionId, requestBody);
    }

}
