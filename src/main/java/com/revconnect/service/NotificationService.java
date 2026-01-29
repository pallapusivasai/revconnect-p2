package com.revconnect.service;
import com.revconnect.model.*;
import com.revconnect.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired private NotificationRepository repo;

    public void send(User u,String msg){
        Notification n=new Notification();n.setUser(u);n.setMessage(msg);repo.save(n);
    }

    public void view(User u){
        System.out.println("\n=== Notifications ===");
        repo.findByUserAndSeenFalse(u).forEach(n->{System.out.println("🔔 "+n.getMessage());n.setSeen(true);repo.save(n);});
    }
}
