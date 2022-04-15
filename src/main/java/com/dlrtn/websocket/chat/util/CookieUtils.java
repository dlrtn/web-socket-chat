package com.dlrtn.websocket.chat.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieUtils {

    public static String getCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, Integer.MAX_VALUE);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
