package com.dlrtn.websocket.chat.controller;

import com.dlrtn.websocket.chat.model.payload.SignInRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;

@RequiredArgsConstructor
public class SecurityController {

    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/sign-in")
    public String customLoginProcess(
            @Valid @RequestBody SignInRequest signInRequest
    ) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/sign-in";

    }


}
