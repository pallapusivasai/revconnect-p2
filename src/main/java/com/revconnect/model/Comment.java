package com.revconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne private Post post;
    @ManyToOne private User user;
    private String text;
    public void setPost(Post p){post=p;}
    public void setUser(User u){user=u;}
    public void setText(String t){text=t;}
}
