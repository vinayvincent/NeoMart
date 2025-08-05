package com.neomart.auth.controller;

import com.neomart.auth.dto.AuthResponse;
import com.neomart.auth.dto.LoginRequest;
import com.neomart.auth.dto.RegisterRequest;
import com.neomart.auth.entity.User;
import com.neomart.auth.service.AuthService;
import com.neomart.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2Success(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            
            // Extract user information from OAuth2
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String username = email != null ? email : oauth2User.getName();
            
            // Create a temporary user details object for JWT generation
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
                username, "", oauth2User.getAuthorities()
            );
            
            String token = jwtUtil.generateToken(userDetails, email);
            
            AuthResponse response = new AuthResponse(
                token,
                username,
                email,
                "USER"
            );
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/oauth2/failure")
    public ResponseEntity<Map<String, String>> oauth2Failure() {
        return ResponseEntity.badRequest().body(Map.of("error", "OAuth2 authentication failed"));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                return ResponseEntity.ok(Map.of(
                    "valid", true,
                    "username", username
                ));
            } catch (Exception e) {
                return ResponseEntity.ok(Map.of("valid", false));
            }
        }
        
        return ResponseEntity.ok(Map.of("valid", false));
    }
} 