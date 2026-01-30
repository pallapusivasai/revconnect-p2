package com.revconnect.exception;

public class RevConnectException extends RuntimeException {

    public RevConnectException(String message) {
        super(message);
    }

    // optional helper methods
    public static RevConnectException userNotFound(String email) {
        return new RevConnectException("User not found with email: " + email);
    }

    public static RevConnectException userAlreadyExists(String email) {
        return new RevConnectException("User already exists with email: " + email);
    }

    public static RevConnectException invalidCredentials() {
        return new RevConnectException("Invalid email or password");
    }
}
