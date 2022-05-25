package com.dlrtn.websocket.chat.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class SecurityUtils implements PasswordEncoder {

    @Autowired
    private static PasswordEncoder passwordEncoder;

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return PasswordEncoder.super.upgradeEncoding(encodedPassword);
    }

}
