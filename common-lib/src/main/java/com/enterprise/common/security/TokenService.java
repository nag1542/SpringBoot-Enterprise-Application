package com.enterprise.common.security;

import java.util.Map;

public interface TokenService {
    String generateToken(String username, Map<String, Object> claims);
}
