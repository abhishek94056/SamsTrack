package com.abhishek.SamsTrack.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	//minimum 256-bit (32+ chars)
	private static final String SECRET = "samsTrackSuperSecretKeyForJwtAuthentication12345";

	private static final long EXPIRY = 1000 * 60 * 60 * 24; // 24 hours

	public String generateToken(String username) {

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRY))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256).compact();
	}
}
