package com.dlrtn.websocket.chat.controller;

import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.domain.dto.SignInRequest;
import com.dlrtn.websocket.chat.model.domain.dto.SignUpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dlrtn.websocket.chat.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입 페이지로 이동")
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signup";
    }

    @Operation(summary = "회원가입 후 로그인 페이지로 이동")
    @PostMapping("/signUp")
    public String signUp(SignUpRequest request) {
        userService.signUp(request);
        return "redirect:/signin"; //로그인 구현 예정
    }

    @Operation(summary = "회원 로그인 페이지")
    @GetMapping("/signIn")
    public String signInForm(SignInRequest request) {
        return "/signin"; //로그인 구현 예정
    }

    @Operation(summary = "회원 로그인 로직 처리")
    @PostMapping("/signIn")
    public String signIn(SignInRequest signinrequest, HttpServletRequest sessionrequest) {
        User signinuser = userService.signIn(signinrequest);
        if (signinuser == null) {
            // 해당하는 유저가 없음
            return "redirect:/signIn";
        }
        // 해당하는 유저를 찾았음..!

        HttpSession session = sessionrequest.getSession();
        session.setAttribute("signinuser", signinuser);

        return "redirect:/userinfo"; //로그인 성공 후 보낼 곳 찾아야됨
    }

}
