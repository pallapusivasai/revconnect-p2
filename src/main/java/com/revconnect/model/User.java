package com.revconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    public Long getId(){ return id; }
    public String getEmail(){ return email; }
    public String getPassword(){ return password; }

    public void setEmail(String e){ email = e; }
    public void setPassword(String p){ password = p; }
}
