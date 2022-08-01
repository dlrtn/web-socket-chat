package com.dlrtn.websocket.chat.business.user.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.*;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserPageController {

    @Operation(summary = "회원 가입 페이지")
    @GetMapping("/sign-up")
    public String getSignUpPage() {
        return SIGNUP;
    }

    @Operation(summary = "회원 로그인 페이지")
    @GetMapping("/sign-in")
    public String getSignInPage() {
        return LOGIN;
    }

    @Operation(summary = "회원 정보 수정 페이지")
    @GetMapping("/{userId}/modifying")
    public String getChangeUserInfoPage() {
        return MODIFYING;
    }

    @Operation(summary = "회원 탈퇴 페이지")
    @GetMapping("/{userId}/withdrawal")
    public String getWithdrawalUserPage() {
        return WITHDRAWAL;
    }

}
