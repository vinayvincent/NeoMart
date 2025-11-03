package com.neomart.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "oauth_providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthProvider {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 50)
    private String name;
    
    @Column(name = "client_id", nullable = false)
    private String clientId;
    
    @Column(name = "client_secret", nullable = false)
    private String clientSecret;
    
    @Column(name = "authorization_uri", nullable = false, length = 500)
    private String authorizationUri;
    
    @Column(name = "token_uri", nullable = false, length = 500)
    private String tokenUri;
    
    @Column(name = "user_info_uri", nullable = false, length = 500)
    private String userInfoUri;
    
    @Column(nullable = false)
    private String scope;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}