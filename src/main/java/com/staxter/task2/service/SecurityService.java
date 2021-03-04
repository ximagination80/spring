package com.staxter.task2.service;

import com.google.common.base.Preconditions;
import com.staxter.task2.exception.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SecurityService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final PasswordEncoder passwordEncoder;

    public boolean matches(String encodedPassword, String openUserPassword) {
        Preconditions.checkNotNull(encodedPassword);
        return encodedPassword.equals(encode(openUserPassword));
    }

    public String encode(String password) {
        Preconditions.checkNotNull(password);
        return passwordEncoder.encode(password);
    }

    public String generateToken(String userName) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder().setSubject(userName).setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String parseToken(String userName) throws InvalidTokenException{
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(userName).getBody().getSubject();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
