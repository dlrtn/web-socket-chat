package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.UserSessionCreation;
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
    public CommonResponse signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return userService.signUp(signUpRequest);
    }

    @Operation(summary = "회원 로그인")
    @PostMapping(LOGIN)
    public CommonResponse signIn(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody SignInRequest requestBody
    ) {
        log.info("[Request Body] username : {}, userPW : {}", requestBody.getUsername(), requestBody.getPassword());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        UserSessionCreation sessionCreation = userService.signIn(sessionId, requestBody);

        if (sessionCreation.isSuccess()) {
            CookieUtils.setCookie(response, UserSessionConstants.SESSION_ID_COOKIE_NAME, sessionCreation.getSessionId());
            return CommonResponse.success();
        } else {
            return CommonResponse.failWith(sessionCreation.getFailReason());
        }
    }

    @Operation(summary = "회원 정보수정")
    @PatchMapping(MODIFYING)
    public CommonResponse update(
            HttpServletRequest request,
            @Valid @RequestBody UserInfoUpdateRequest requestBody
    ) {
        log.info("[Update RequestBody] username : {}, password : {}, realName : {}, newPassword : {}", requestBody.getUsername(), requestBody.getExistingPassword(), requestBody.getNewRealName(), requestBody.getNewPassword());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        return userService.update(sessionId, requestBody);
    }

    @Operation(summary = "회원 정보 삭제")
    @DeleteMapping(WITHDRAWAL)
    public CommonResponse deleteUser(
            HttpServletRequest request,
            @Valid @RequestBody DeleteUserRequest requestBody
    ) {
        log.info("[Update RequestBody] username : {}, userPW : {}", requestBody.getUsername(), requestBody.getPassword());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        return userService.deleteUser(sessionId, requestBody);
    }

}
