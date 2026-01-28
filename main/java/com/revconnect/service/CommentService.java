package com.revconnect.service;
import com.revconnect.model.*;
import com.revconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class CommentService {
    @Autowired private CommentRepository repo;
    @Autowired private PostRepository postRepo;
    @Autowired private NotificationService notify;

    public String comment(User u,Scanner sc){
        System.out.print("Enter Post ID: ");
        Post p=postRepo.findById(Long.parseLong(sc.nextLine())).orElse(null);
        if(p==null) return "❌ Post not found";
        System.out.print("Enter comment: ");
        Comment c=new Comment();c.setPost(p);c.setUser(u);c.setText(sc.nextLine());
        repo.save(c);
        if(!p.getUser().getId().equals(u.getId()))
            notify.send(p.getUser(),u.getEmail()+" commented on your post 💬");
        return "💬 Comment added";
    }
}
