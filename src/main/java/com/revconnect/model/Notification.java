package com.revconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // ✅ FIX 1
    private User user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean seen = false;

    // ✅ REQUIRED getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSeen() {
        return seen;
    }

    // setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
