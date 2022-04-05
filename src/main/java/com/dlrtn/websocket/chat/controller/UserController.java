package com.dlrtn.websocket.chat.controller;

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

	@Operation
	@PostMapping("/signUp")
	public String signUp(@RequestBody User user) {
		userService.joinUser(user);
		return "redirect:/login"; //로그인 구현 예정
	}
}