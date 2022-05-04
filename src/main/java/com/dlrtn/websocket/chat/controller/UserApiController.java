package com.dlrtn.websocket.chat.controller;

import com.dlrtn.websocket.chat.model.UserSessionConstants;
import com.dlrtn.websocket.chat.model.UserSessionCreation;
import com.dlrtn.websocket.chat.model.payload.*;
import com.dlrtn.websocket.chat.service.UserService;
import com.dlrtn.websocket.chat.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/sign-up")
    public CommonResponse signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return userService.signUp(signUpRequest);
    }

    @Operation(summary = "회원 로그인")
    @PostMapping("/sign-in")
    public CommonResponse signIn(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody SignInRequest requestBody
    ) {
        LOGGER.info("[Request Body] username : {}, userPW : {}", requestBody.getusername(), requestBody.getPassword());
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
    @PatchMapping("/modifying")
    public CommonResponse update(
            HttpServletRequest request,
            @Valid @RequestBody UserInfoUpdateRequest requestBody
    ) {
        LOGGER.info("[Update RequestBody] username : {}, password : {}, realName : {}, newPassword : {}", requestBody.getusername(), requestBody.getExistingPassword(), requestBody.getNewRealName(), requestBody.getNewPassword());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        return userService.update(sessionId, requestBody);
    }

    @Operation(summary = "회원 정보 삭제")
    @DeleteMapping("/withdrawal")
    public CommonResponse deleteUser(
            HttpServletRequest request,
            @Valid @RequestBody DeleteUserRequest requestBody
    ) {
        LOGGER.info("[Update RequestBody] username : {}, userPW : {}", requestBody.getusername(), requestBody.getPassword());
        String sessionId = CookieUtils.getCookie(request, UserSessionConstants.SESSION_ID_COOKIE_NAME);
        return userService.deleteUser(sessionId, requestBody);
    }

}
