package com.gdsc.festivalholic;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "sessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public String createSession(Object value, HttpServletResponse response) {

        //세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

//        String cookie = "sessionId=" + sessionId +";domain=localhost;max-age=604800;path=/;SameSite=None;";
        //쿠키 생성
        ResponseCookie cookie = ResponseCookie.from(SESSION_COOKIE_NAME, sessionId)
                .path("/")
                .sameSite("None")
                .httpOnly(false)
                .secure(true)
                .maxAge(604800)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
//        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
//        mySessionCookie.setPath("/");
//        mySessionCookie.setDomain("localhost");
//        mySessionCookie.setHttpOnly(true);
//        mySessionCookie.setMaxAge(604800);
//        mySessionCookie.setAttribute("SameSite", "None");
//        mySessionCookie.setDomain("3.34.177.220");
//        response.addCookie(mySessionCookie);

//        response.setHeader("Set-Cookie", cookie);
        return sessionId;
    }

    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

    public String findMySessionId(HttpServletResponse response) {
        return response.getHeader("Set-Cookie");
    }

}
