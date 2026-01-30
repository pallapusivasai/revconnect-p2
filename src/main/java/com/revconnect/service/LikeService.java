package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.LikeEntity;
import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.repository.LikeRepository;
import com.revconnect.repository.PostRepository;
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

    /**
     * Like a post
     */
    public void like(User user, Long postId) {

        if (user == null || postId == null) {
            logger.warn("Invalid like request: user or postId is null");
            throw new RevConnectException("Invalid like request");
        }

        try {
            Post post = postRepo.findById(postId)
                    .orElseThrow(() -> {
                        logger.warn("Like failed - post {} not found", postId);
                        return new RevConnectException("Post not found");
                    });

            if (likeRepo.findByPostAndUser(post, user).isPresent()) {
                logger.warn("User {} already liked post {}", user.getEmail(), postId);
                throw new RevConnectException("Post already liked");
            }

            LikeEntity like = new LikeEntity();
            like.setPost(post);
            like.setUser(user);

            likeRepo.save(like);

            // Notification (do not notify self)
            if (!post.getUser().getId().equals(user.getId())) {
                notify.send(
                        post.getUser(),
                        user.getEmail() + " liked your post ❤️"
                );
            }

            logger.info("Post {} liked by {}", postId, user.getEmail());

        } catch (RevConnectException e) {
            throw e; // already logged
        } catch (Exception e) {
            logger.error("Error while liking post {} by {}", postId, user.getEmail(), e);
            throw new RevConnectException("Failed to like post");
        }
    }

    /**
     * Unlike a post
     */
    public void unlike(User user, Long postId) {

        if (user == null || postId == null) {
            logger.warn("Invalid unlike request: user or postId is null");
            throw new RevConnectException("Invalid unlike request");
        }

        try {
            Post post = postRepo.findById(postId)
                    .orElseThrow(() -> {
                        logger.warn("Unlike failed - post {} not found", postId);
                        return new RevConnectException("Post not found");
                    });

            LikeEntity like = likeRepo.findByPostAndUser(post, user)
                    .orElseThrow(() -> {
                        logger.warn("User {} tried to unlike post {} without liking it",
                                user.getEmail(), postId);
                        return new RevConnectException("You have not liked this post");
                    });

            likeRepo.delete(like);

            logger.info("Post {} unliked by {}", postId, user.getEmail());

        } catch (RevConnectException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while unliking post {} by {}", postId, user.getEmail(), e);
            throw new RevConnectException("Failed to unlike post");
        }
    }
}
