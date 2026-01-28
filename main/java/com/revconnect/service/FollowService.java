package com.revconnect.service;
import com.revconnect.model.*;
import com.revconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class FollowService {
    @Autowired private UserRepository userRepo;
    @Autowired private FollowRepository followRepo;

    public String follow(User u,Scanner sc){
        System.out.print("Enter user email to follow: ");
        User t=userRepo.findByEmail(sc.nextLine()).orElse(null);
        if(t==null) return "❌ User not found";
        if(followRepo.findByFollowerAndFollowing(u,t).isPresent()) return "⚠ Already following";
        Follow f=new Follow();f.setFollower(u);f.setFollowing(t);followRepo.save(f);
        return "➕ Followed successfully";
    }

    public String unfollow(User u,Scanner sc){
        System.out.print("Enter user email to unfollow: ");
        User t=userRepo.findByEmail(sc.nextLine()).orElse(null);
        if(t==null) return "❌ User not found";
        return followRepo.findByFollowerAndFollowing(u,t).map(f->{followRepo.delete(f);return "➖ Unfollowed";}).orElse("⚠ Not following");
    }
}
