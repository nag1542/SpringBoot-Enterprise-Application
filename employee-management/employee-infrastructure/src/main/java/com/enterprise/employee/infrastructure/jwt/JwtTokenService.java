package com.enterprise.employee.infrastructure.jwt;

import com.enterprise.common.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {

    private final JwtService jwtService;

    @Override
    public String generateToken(String username, Map<String, Object> claims) {
        return jwtService.generateToken(username, claims);
    }
}
