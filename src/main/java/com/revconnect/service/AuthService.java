package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.User;
import com.revconnect.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger =
            LogManager.getLogger(AuthService.class);

    // BCrypt safely supports up to 72 bytes
    private static final int MAX_PASSWORD_LENGTH = 72;

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    public void register(String email, String password) {

        logger.info("Registration attempt for email: {}", email);

        validateCredentials(email, password);

        if (password.length() > MAX_PASSWORD_LENGTH) {
            logger.warn("Password too long for email: {}", email);
            throw new RevConnectException(
                    "Password must not exceed 72 characters"
            );
        }

        if (repo.findByEmail(email).isPresent()) {
            logger.warn("User already exists: {}", email);
            throw RevConnectException.userAlreadyExists(email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(encoder.encode(password));

        repo.save(user);

        logger.info("Registration successful for email: {}", email);
    }

    public User login(String email, String password) {

        logger.info("Login attempt for email: {}", email);

        validateCredentials(email, password);

        User user = repo.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User not found: {}", email);
                    return RevConnectException.invalidCredentials();
                });

        if (!encoder.matches(password, user.getPassword())) {
            logger.warn("Invalid password for email: {}", email);
            throw RevConnectException.invalidCredentials();
        }

        logger.info("Login successful for email: {}", email);
        return user;
    }

    /* ===================== PRIVATE HELPERS ===================== */

    private void validateCredentials(String email, String password) {

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {
            throw new RevConnectException(
                    "Email and password cannot be empty"
            );
        }
    }
}
