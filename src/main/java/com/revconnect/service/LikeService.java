package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.*;
import com.revconnect.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private static final Logger logger =
            LogManager.getLogger(LikeService.class);

    private final LikeRepository likeRepo;
    private final PostRepository postRepo;
    private final NotificationService notify;

    public LikeService(LikeRepository likeRepo,
                       PostRepository postRepo,
                       NotificationService notify) {
        this.likeRepo = likeRepo;
        this.postRepo = postRepo;
        this.notify = notify;
    }

    public void like(User user, Long postId) {

        if (user == null || postId == null) {
            throw new RevConnectException("Invalid like request");
        }

        Post post = postRepo.findById(postId)
                .orElseThrow(() ->
                        new RevConnectException("Post not found"));

        if (likeRepo.findByPostAndUser(post, user).isPresent()) {
            throw new RevConnectException("Post already liked");
        }

        LikeEntity like = new LikeEntity();
        like.setPost(post);
        like.setUser(user);

        likeRepo.save(like);

        // ✅ notification (already correct)
        if (!post.getUser().getId().equals(user.getId())) {
            notify.send(
                    post.getUser(),
                    user.getEmail() + " liked your post ❤️"
            );
        }

        logger.info("Post {} liked by {}", postId, user.getEmail());
    }

    public void unlike(User user, Long postId) {

        if (user == null || postId == null) {
            throw new RevConnectException("Invalid unlike request");
        }

        Post post = postRepo.findById(postId)
                .orElseThrow(() ->
                        new RevConnectException("Post not found"));

        LikeEntity like = likeRepo.findByPostAndUser(post, user)
                .orElseThrow(() ->
                        new RevConnectException("You have not liked this post"));

        likeRepo.delete(like);

        logger.info("Post {} unliked by {}", postId, user.getEmail());
    }
}
