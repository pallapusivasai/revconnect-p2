package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
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

    public CommentService(CommentRepository repo,
                          PostRepository postRepo,
                          NotificationService notify) {
        this.repo = repo;
        this.postRepo = postRepo;
        this.notify = notify;
    }

    public void comment(User user, Long postId, String text) {

        if (user == null || postId == null || text == null || text.isBlank()) {
            throw new RevConnectException("Invalid comment request");
        }

        Post post = postRepo.findById(postId)
                .orElseThrow(() ->
                        new RevConnectException("Post not found"));

        Comment c = new Comment();
        c.setPost(post);
        c.setUser(user);
        c.setText(text);

        repo.save(c);

        // ✅ notification (already correct)
        if (!post.getUser().getId().equals(user.getId())) {
            notify.send(
                    post.getUser(),
                    user.getEmail() + " commented on your post 💬"
            );
        }

        logger.info("User {} commented on post {}", user.getEmail(), postId);
    }
}
