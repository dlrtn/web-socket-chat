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
    public String signUp(SignUpRequest signUpRequest) {
        userService.signUp(signUpRequest);
        return "redirect:/signIn";
    }

    @Operation(summary = "회원 로그인 페이지")
    @GetMapping("/signIn")
    public String signInForm(SignInRequest request) {
        return "singin";
    }

    @Operation(summary = "회원 로그인 로직 처리")
    @PostMapping("/signIn")
    public String signIn(SignInRequest signInRequest, HttpServletRequest sessionReqeust) {
        if (signInRequest.getPassword() == null || signInRequest.getPassword() == null) {
            // 내용을 입력하지 않았을 때 예외 처리 들어가야될듯, 다른 데서 처리해야되나..????
        }

        User loginUser = userService.signIn(signInRequest); // 이게 맞나 싶음

        if (loginUser == null) {
            // 해당하는 유저가 없음
            return "redirect:/signIn"; // id, pw 모두 입력했으나 유저를 찾지 못했을 때, 예외 처리 후, 리다이렉토!
        }
        // 해당하는 유저를 찾았음..!

        HttpSession session = sessionReqeust.getSession(); // 세션 생성
        session.setAttribute("loginUser", loginUser);

        return "redirect:/userinfo"; //로그인 성공 후 보낼 곳 찾아야됨
    }

}
