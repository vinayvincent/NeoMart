package com.neomart.auth.service;

import com.neomart.auth.dto.AuthResponse;
import com.neomart.auth.entity.Role;
import com.neomart.auth.entity.User;
import com.neomart.auth.entity.UserOAuthConnection;
import com.neomart.auth.entity.OAuthProvider;
import com.neomart.auth.repository.RoleRepository;
import com.neomart.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    
    @Transactional
    public AuthResponse processOAuthLogin(OAuth2User oAuth2User, String providerName) {
        String email = oAuth2User.getAttribute("email");
        String providerId = oAuth2User.getAttribute("sub");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");
        
        User user = userRepository.findByOAuthProvider(providerName, providerId)
                .orElseGet(() -> createOrLinkUser(email, firstName, lastName, providerId, providerName));
        
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        
        return createAuthResponse(user, accessToken, refreshToken);
    }
    
    private User createOrLinkUser(String email, String firstName, String lastName, String providerId, String providerName) {
        User user = userRepository.findByEmail(email).orElse(null);
        
        if (user == null) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            
            user = new User();
            user.setUsername(email);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setIsEmailVerified(true);
            user.setRoles(Set.of(userRole));
            user = userRepository.save(user);
        }
        
        return user;
    }
    
    private AuthResponse createAuthResponse(User user, String accessToken, String refreshToken) {
        AuthResponse.UserInfo userInfo = new AuthResponse.UserInfo(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
        
        return new AuthResponse(accessToken, refreshToken, "Bearer", 86400L, userInfo);
    }
}