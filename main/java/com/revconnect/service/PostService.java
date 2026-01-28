package com.revconnect.service;
import com.revconnect.model.*;
import com.revconnect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class PostService {
    @Autowired private PostRepository repo;
    public String createPost(User u,Scanner sc){
        System.out.print("Enter post content: ");
        String c=sc.nextLine();
        Post p=new Post();p.setUser(u);p.setContent(c);repo.save(p);
        return "✅ Post created";
    }
    public void viewMyPosts(User u){
        System.out.println("\n=== My Posts ===");
        repo.findByUser(u).forEach(p->System.out.println("Post ID "+p.getId()+": "+p.getContent()));
    }
    public void viewFeed(){
        System.out.println("\n=== Feed ===");
        repo.findAllByOrderByIdDesc().forEach(p->System.out.println("Post ID "+p.getId()+": "+p.getContent()));
    }
}
