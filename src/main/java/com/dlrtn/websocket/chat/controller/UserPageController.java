package com.dlrtn.websocket.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserPageController {

    @Operation(summary = "회원가입 페이지로 이동")
    @GetMapping("/sign-up")
    public String getSignUpPage() {
        return "signup";
    }

    @Operation(summary = "회원 로그인 페이지")
    @GetMapping("/sing-in")
    public String getSignInPage() {
        return "signin";
    }

}
