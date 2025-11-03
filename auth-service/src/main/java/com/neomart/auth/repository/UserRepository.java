package com.neomart.auth.repository;

import com.neomart.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u JOIN u.oauthConnections oc WHERE oc.provider.name = :providerName AND oc.providerUserId = :providerUserId")
    Optional<User> findByOAuthProvider(String providerName, String providerUserId);
}