package org.tinder.utils;

import org.tinder.enums.CookieNames;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public final class CookieWorker {
    public static Optional<String> tryGetCookie(Cookie[] cookies, CookieNames cookieNames) {
        if (cookies == null) return Optional.empty();
        return Arrays.stream(cookies)
                .filter(c -> cookieNames.equals(c.getName()))
                .findAny()
                .map(Cookie::getValue);
    }

    public static String getCookieOrThrow(HttpServletRequest req, CookieNames cookieNames) {
        Cookie[] cookies = req.getCookies();
        return tryGetCookie(cookies, cookieNames)
                .orElseThrow(() -> new RuntimeException("Cookie is absent"));
    }

    private static void set(HttpServletResponse res, Cookie cookie) {
        res.addCookie(cookie);
        res.addHeader("Access-Control-Expose-Headers", "Set-Cookie");
    }

    public static Integer getUsersOffset(HttpServletRequest req) {
        try {
            String stringOffset = getCookieOrThrow(req, CookieNames.USERS_OFFSET);
            return Integer.parseInt(stringOffset);
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setUsersOffset(HttpServletResponse res, Integer value) {
        Cookie cookie = new Cookie(CookieNames.USERS_OFFSET.getValue(), value.toString());
        set(res, cookie);
    }
}
