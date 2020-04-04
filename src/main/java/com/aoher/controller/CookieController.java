package com.aoher.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class CookieController {

    private static final String COOKIE_USERNAME = "username";

    @GetMapping("/")
    public String readCookies(@CookieValue(value = COOKIE_USERNAME, defaultValue = "Atta") String username) {
        return String.format("Hey! My username is %s", username);
    }

    @GetMapping("/change-username")
    public String setCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_USERNAME, "Jovan");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return "Username is changed!";
    }

    @GetMapping("/delete-username")
    public String deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_USERNAME, null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return "Username is deleted!";
    }

    @GetMapping("/all-cookies")
    public String readAllCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue())
                    .collect(Collectors.joining(", "));
        }
        return "No cookies";
    }
}
