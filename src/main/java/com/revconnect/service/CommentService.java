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
    public void comment(User user, Long postId, String text) {

        if (user == null || postId == null) {
            logger.warn("Invalid comment request: user or postId is null");
            throw new RevConnectException("Invalid comment request");
        }

        if (text == null || text.isBlank()) {
            logger.warn("User {} attempted to post empty comment", user.getEmail());
            throw new RevConnectException("Comment text cannot be empty");
        }

        try {
            Post post = postRepo.findById(postId)
                    .orElseThrow(() -> {
                        logger.warn("Comment failed - post {} not found", postId);
                        return new RevConnectException("Post not found");
                    });

            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(user);
            comment.setText(text);

            repo.save(comment);

            // Notification (do not notify self)
            if (!post.getUser().getId().equals(user.getId())) {
                notify.send(
                        post.getUser(),
                        user.getEmail() + " commented on your post 💬"
                );
            }

            logger.info("User {} commented on post {}", user.getEmail(), postId);

        } catch (RevConnectException e) {
            throw e; // already logged
        } catch (Exception e) {
            logger.error(
                    "Error while commenting on post {} by {}",
                    postId,
                    user.getEmail(),
                    e
            );
            throw new RevConnectException("Failed to add comment");
        }
    }
}
