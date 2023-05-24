package com.vtes.sercurity.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

	@Value("${vtes.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	@Value("${vtes.app.jwtRefreshExpirationMs}")
	private int jwtRefreshExpirationMs;

	public void createCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly,
			boolean secure, String path) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setHttpOnly(httpOnly);
		cookie.setSecure(secure);
		cookie.setPath(path);
		response.addCookie(cookie);
	}

	public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					cookie.setValue(null);
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
		}
	}

	public void createAccessTokenCookie(HttpServletResponse response, String accessToken) {
		createCookie(response, "accessToken", accessToken, jwtExpirationMs, true, false, "/");
	}

	public void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
		createCookie(response, "refreshToken", refreshToken, jwtRefreshExpirationMs, true, false, "/");
	}
}
