package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.Comment;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.CommentRepository;
import com.revconnect.repository.PostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Add a comment to a post
     */
    @Transactional
    public void comment(User user, Long postId, String text) {

        if (user == null || postId == null) {
            throw new RevConnectException("Invalid comment request");
        }

        if (text == null || text.isBlank()) {
            throw new RevConnectException("Comment text cannot be empty");
        }

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RevConnectException("Post not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(text);

        // ✅ Save comment (must not rollback)
        repo.saveAndFlush(comment);

        // ✅ Notification should NEVER break comment save
        try {
            if (!post.getUser().getId().equals(user.getId())) {
                notify.send(
                        post.getUser(),
                        user.getEmail() + " commented on your post 💬"
                );
            }
        } catch (Exception e) {
            logger.error("Notification failed, comment saved successfully", e);
        }

        logger.info("User {} commented on post {}", user.getEmail(), postId);
    }
}
