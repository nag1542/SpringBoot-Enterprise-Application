package com.enterprise.employee.api.controller;

import com.enterprise.common.model.ApiResponse;
import com.enterprise.employee.api.request.LoginRequest;
import com.enterprise.employee.api.response.LoginResponse;
import com.enterprise.common.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        String token = tokenService.generateToken(request.username(), Map.of("role", "USER"));
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Login successful", new LoginResponse(token)));
    }
}
