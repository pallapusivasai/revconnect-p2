package com.revconnect.service;

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

    public LikeService(
            LikeRepository likeRepo,
            PostRepository postRepo,
            NotificationService notify
    ) {
        this.likeRepo = likeRepo;
        this.postRepo = postRepo;
        this.notify = notify;
    }

    public String like(User user, Long postId) {

        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) return "❌ Post not found";

        if (likeRepo.findByPostAndUser(post, user).isPresent())
            return "⚠ Already liked";

        LikeEntity l = new LikeEntity();
        l.setPost(post);
        l.setUser(user);
        likeRepo.save(l);

        if (!post.getUser().getId().equals(user.getId())) {
            notify.send(post.getUser(),
                    user.getEmail() + " liked your post ❤️");
        }

        logger.info("Post {} liked by {}", postId, user.getEmail());
        return "❤️ Post liked";
    }

    public String unlike(User user, Long postId) {

        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) return "❌ Post not found";

        return likeRepo.findByPostAndUser(post, user)
                .map(l -> {
                    likeRepo.delete(l);
                    logger.info("Post {} unliked by {}", postId, user.getEmail());
                    return "💔 Post unliked";
                })
                .orElse("⚠ Not liked yet");
    }
}
