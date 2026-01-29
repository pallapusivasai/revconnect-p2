package com.revconnect.service;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.PostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private static final Logger logger =
            LogManager.getLogger(PostService.class);

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public String createPost(User user, String content) {

        Post post = new Post();
        post.setUser(user);
        post.setContent(content);

        repo.save(post);

        logger.info("Post created by user {}", user.getEmail());
        return "✅ Post created";
    }

    public void viewMyPosts(User user) {
        logger.info("Viewing posts for user {}", user.getEmail());
        repo.findByUser(user)
                .forEach(p -> System.out.println("📝 " + p.getContent()));
    }

    public void viewFeed() {
        logger.info("Viewing feed");
        repo.findAllByOrderByIdDesc()
                .forEach(p ->
                        System.out.println(p.getUser().getEmail() + ": " + p.getContent()));
    }
}
