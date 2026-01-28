package com.revconnect.model;
import jakarta.persistence.*;

@Entity
@Table(name="notifications")
public class Notification {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne private User user;
    private String message;
    private boolean seen=false;
    public void setUser(User u){user=u;}
    public void setMessage(String m){message=m;}
    public void setSeen(boolean s){seen=s;}
    public String getMessage(){return message;}
    public boolean isSeen(){return seen;}
}
