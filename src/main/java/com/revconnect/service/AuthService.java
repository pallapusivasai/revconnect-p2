package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.User;
import com.revconnect.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger logger =
            LogManager.getLogger(AuthService.class);

    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    // Service performs action, does not return UI message
    public void register(String email, String password) {

        logger.info("Registration attempt for email: {}", email);

        if (email.isBlank() || password.isBlank()) {
            throw new RevConnectException("Email and password cannot be empty");
        }

        if (repo.findByEmail(email).isPresent()) {
            logger.warn("User already exists: {}", email);
            throw RevConnectException.userAlreadyExists(email);
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(password); // (plain text for now)

        repo.save(u);

        logger.info("Registration successful for email: {}", email);
    }

    public User login(String email, String password) {

        logger.info("Login attempt for email: {}", email);

        if (email.isBlank() || password.isBlank()) {
            throw new RevConnectException("Email and password cannot be empty");
        }

        return repo.findByEmailAndPassword(email, password)
                .orElseThrow(() -> {
                    logger.warn("Invalid login for email: {}", email);
                    return RevConnectException.invalidCredentials();
                });
    }
}
