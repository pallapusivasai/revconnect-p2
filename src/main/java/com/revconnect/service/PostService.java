package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
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

    /**
     * Create a new post
     */
    public void createPost(User user, String content) {

        if (user == null) {
            logger.warn("Attempt to create post with NULL user");
            throw new RevConnectException("User cannot be null");
        }

        if (content == null || content.isBlank()) {
            logger.warn("User {} attempted to create empty post", user.getEmail());
            throw new RevConnectException("Post content cannot be empty");
        }

        try {
            Post post = new Post();
            post.setUser(user);
            post.setContent(content);

            repo.save(post);

            logger.info("Post successfully created by user: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Failed to create post for user {}", user.getEmail(), e);
            throw new RevConnectException("Post creation failed");
        }
    }

    /**
     * View posts created by the logged-in user
     */
    public List<Post> viewMyPosts(User user) {

        if (user == null) {
            logger.warn("Attempt to view posts with NULL user");
            throw new RevConnectException("User cannot be null");
        }

        logger.info("Fetching posts for user {}", user.getEmail());

        try {
            return repo.findByUser(user);
        } catch (Exception e) {
            logger.error("Error fetching posts for user {}", user.getEmail(), e);
            throw new RevConnectException("Unable to fetch user posts");
        }
    }

    /**
     * View global feed
     */
    public List<Post> viewFeed() {

        logger.info("Fetching global feed");

        try {
            return repo.findAllByOrderByIdDesc();
        } catch (Exception e) {
            logger.error("Error fetching feed", e);
            throw new RevConnectException("Unable to load feed");
        }
    }
}
