package com.dlrtn.websocket.chat.controller;

import com.dlrtn.websocket.chat.model.domain.dto.SignUpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    @Operation(summary = "회원가입 후 로그인 페이지로 이동")
    @PostMapping("/signUp")
    public String signUp(SignUpRequest req) {
        userService.signUp(req);
        return "redirect:/login"; //로그인 구현 예정
    }

}
