package com.neomart.auth.controller;

import com.neomart.auth.dto.AuthResponse;
import com.neomart.auth.dto.LoginRequest;
import com.neomart.auth.dto.RegisterRequest;
import com.neomart.auth.service.AuthService;
import com.neomart.auth.service.OAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final OAuthService oAuthService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauthSuccess(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestParam String provider) {
        AuthResponse response = oAuthService.processOAuthLogin(principal, provider);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }
}