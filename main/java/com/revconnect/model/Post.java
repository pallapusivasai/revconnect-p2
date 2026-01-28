package com.revconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name="posts")
public class Post {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne private User user;
    private String content;
    public Long getId(){return id;}
    public User getUser(){return user;}
    public String getContent(){return content;}
    public void setUser(User u){user=u;}
    public void setContent(String c){content=c;}
}
