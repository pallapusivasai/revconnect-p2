package com.revconnect.service;
import com.revconnect.model.*;
import com.revconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class LikeService {
    @Autowired private LikeRepository likeRepo;
    @Autowired private PostRepository postRepo;
    @Autowired private NotificationService notify;

    public String like(User u,Scanner sc){
        System.out.print("Enter Post ID to like: ");
        Post p=postRepo.findById(Long.parseLong(sc.nextLine())).orElse(null);
        if(p==null) return "❌ Post not found";
        if(likeRepo.findByPostAndUser(p,u).isPresent()) return "⚠ Already liked";
        LikeEntity l=new LikeEntity();l.setPost(p);l.setUser(u);likeRepo.save(l);
        if(!p.getUser().getId().equals(u.getId()))
            notify.send(p.getUser(),u.getEmail()+" liked your post ❤️");
        return "❤️ Post liked";
    }

    public String unlike(User u,Scanner sc){
        System.out.print("Enter Post ID to unlike: ");
        Post p=postRepo.findById(Long.parseLong(sc.nextLine())).orElse(null);
        if(p==null) return "❌ Post not found";
        return likeRepo.findByPostAndUser(p,u).map(l->{likeRepo.delete(l);return "💔 Post unliked";}).orElse("⚠ Not liked yet");
    }
}
