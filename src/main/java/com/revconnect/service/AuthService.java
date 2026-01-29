package com.revconnect.service;

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

    public String register(String email, String password) {

        logger.info("Registration attempt for email: {}", email);

        if (repo.findByEmail(email).isPresent()) {
            logger.warn("Registration failed - user exists: {}", email);
            return "❌ User already exists";
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        repo.save(u);

        logger.info("Registration successful for email: {}", email);
        return "✅ Registered successfully";
    }

    public User login(String email, String password) {

        logger.info("Login attempt for email: {}", email);

        User user = repo.findByEmailAndPassword(email, password).orElse(null);

        if (user == null)
            logger.warn("Login failed for email: {}", email);
        else
            logger.info("Login successful for email: {}", email);

        return user;
    }
}
