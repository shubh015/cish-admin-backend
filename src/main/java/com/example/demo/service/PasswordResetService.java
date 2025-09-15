package com.example.demo.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.models.user.PasswordResetToken;
import com.example.demo.web.models.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepo;
    private final PasswordResetTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.url.frontend.reset-password}")
    private String resetUrlPrefix;

    @Transactional
    public String requestReset(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unknown email"));

        UUID token = UUID.randomUUID();
        OffsetDateTime expiresAt = OffsetDateTime.now().plusMinutes(30); // 30 min expiry

        PasswordResetToken prt = new PasswordResetToken();
        prt.setToken(token);
        prt.setUser(user);
        prt.setExpiresAt(expiresAt);

        tokenRepo.save(prt);

        // Instead of sending mail, just return the reset link
        return resetUrlPrefix + token.toString();
    }

    @Transactional
    public void resetPassword(UUID token, String newPassword) {
        PasswordResetToken prt = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (prt.isUsed() || prt.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Token expired or already used");
        }

        User user = prt.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        prt.setUsed(true);

        tokenRepo.save(prt);
        userRepo.save(user);
    }

    // periodic cleanup method can be scheduled to delete expired tokens
    @Scheduled(cron = "0 0 * * * ?") // hourly
    public void cleanupExpired() {
        tokenRepo.deleteAllByExpiresAtBefore(OffsetDateTime.now());
    }
}
