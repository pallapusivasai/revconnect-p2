package com.revconnect.service;

import com.revconnect.exception.RevConnectException;
import com.revconnect.model.*;
import com.revconnect.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private static final Logger logger =
            LogManager.getLogger(FollowService.class);

    private final UserRepository userRepo;
    private final FollowRepository followRepo;
    private final NotificationService notify;

    public FollowService(UserRepository userRepo,
                         FollowRepository followRepo,
                         NotificationService notify) {
        this.userRepo = userRepo;
        this.followRepo = followRepo;
        this.notify = notify;
    }

    public void follow(User user, Long targetUserId) {

        if (user == null || targetUserId == null) {
            throw new RevConnectException("Invalid follow request");
        }

        if (user.getId().equals(targetUserId)) {
            throw new RevConnectException("You cannot follow yourself");
        }

        User target = userRepo.findById(targetUserId)
                .orElseThrow(() ->
                        new RevConnectException("User not found"));

        if (followRepo.findByFollowerAndFollowing(user, target).isPresent()) {
            throw new RevConnectException("Already following this user");
        }

        Follow f = new Follow();
        f.setFollower(user);
        f.setFollowing(target);
        followRepo.save(f);

        // ✅ MISSING PIECE (NOW FIXED)
        notify.send(
                target,
                user.getEmail() + " started following you ➕"
        );

        logger.info("{} followed {}", user.getEmail(), target.getEmail());
    }

    public void unfollow(User user, Long targetUserId) {

        if (user == null || targetUserId == null) {
            throw new RevConnectException("Invalid unfollow request");
        }

        if (user.getId().equals(targetUserId)) {
            throw new RevConnectException("You cannot unfollow yourself");
        }

        User target = userRepo.findById(targetUserId)
                .orElseThrow(() ->
                        new RevConnectException("User not found"));

        Follow follow = followRepo.findByFollowerAndFollowing(user, target)
                .orElseThrow(() ->
                        new RevConnectException("You are not following this user"));

        followRepo.delete(follow);

        logger.info("{} unfollowed {}", user.getEmail(), target.getEmail());
    }
}
