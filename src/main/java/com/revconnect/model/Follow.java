package com.revconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name="follows", uniqueConstraints = {
    @UniqueConstraint(columnNames={"follower_id","following_id"})
})
public class Follow {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne private User follower;
    @ManyToOne private User following;
    public void setFollower(User u){follower=u;}
    public void setFollowing(User u){following=u;}
}
