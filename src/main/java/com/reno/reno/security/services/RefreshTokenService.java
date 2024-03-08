package com.reno.reno.security.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.reno.reno.model.auth.RefreshTokenEntity;
import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.exception.TokenRefreshException;
import com.reno.reno.repository.auth.RefreshTokenRepository;
import com.reno.reno.repository.auth.UserRepository;

@Service
public class RefreshTokenService {
    @Value("${jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private @Autowired RefreshTokenRepository refreshTokenRepository;

    private @Autowired UserRepository userRepository;

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshTokenEntity createRefreshToken(Long userId) {
        UserEntity user = userRepository.findById(userId).get();

        Optional<RefreshTokenEntity> oldRefreshToken = refreshTokenRepository.findByUser(user);
        if (oldRefreshToken.isPresent()) {
            RefreshTokenEntity refreshToken = oldRefreshToken.get();
            setIsDeleteRefreshToken(refreshToken);
        }

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            String tokenStr = token.getToken();
            setIsDeleteRefreshToken(token);
            // refreshTokenRepository.delete(token);
            throw new TokenRefreshException(tokenStr,
                    "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    public void setIsDeleteRefreshToken(RefreshTokenEntity refreshToken) {
        refreshToken.setIsDeleted(Boolean.TRUE);
        refreshTokenRepository.save(refreshToken);
    }
}
