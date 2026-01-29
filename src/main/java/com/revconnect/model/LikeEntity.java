package com.revconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name="likes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"post_id","user_id"})
})
public class LikeEntity {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne private Post post;
    @ManyToOne private User user;
    public void setPost(Post p){post=p;}
    public void setUser(User u){user=u;}
}
