package com.revconnect.service;

import com.revconnect.model.*;
import com.revconnect.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private static final Logger logger =
            LogManager.getLogger(CommentService.class);

    private final CommentRepository repo;
    private final PostRepository postRepo;
    private final NotificationService notify;

    public CommentService(
            CommentRepository repo,
            PostRepository postRepo,
            NotificationService notify
    ) {
        this.repo = repo;
        this.postRepo = postRepo;
        this.notify = notify;
    }

    public String comment(User user, Long postId, String text) {

        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) return "❌ Post not found";

        Comment c = new Comment();
        c.setPost(post);
        c.setUser(user);
        c.setText(text);

        repo.save(c);

        if (!post.getUser().getId().equals(user.getId())) {
            notify.send(post.getUser(),
                    user.getEmail() + " commented on your post 💬");
        }

        logger.info("User {} commented on post {}", user.getEmail(), postId);
        return "💬 Comment added";
    }
}
