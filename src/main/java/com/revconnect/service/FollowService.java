package com.revconnect.service;

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

    public FollowService(
            UserRepository userRepo,
            FollowRepository followRepo
    ) {
        this.userRepo = userRepo;
        this.followRepo = followRepo;
    }

    public String follow(User user, Long targetUserId) {

        User target = userRepo.findById(targetUserId).orElse(null);
        if (target == null) return "❌ User not found";

        if (followRepo.findByFollowerAndFollowing(user, target).isPresent())
            return "⚠ Already following";

        Follow f = new Follow();
        f.setFollower(user);
        f.setFollowing(target);
        followRepo.save(f);

        logger.info("{} followed {}", user.getEmail(), target.getEmail());
        return "➕ Followed successfully";
    }

    public String unfollow(User user, Long targetUserId) {

        User target = userRepo.findById(targetUserId).orElse(null);
        if (target == null) return "❌ User not found";

        return followRepo.findByFollowerAndFollowing(user, target)
                .map(f -> {
                    followRepo.delete(f);
                    logger.info("{} unfollowed {}", user.getEmail(), target.getEmail());
                    return "➖ Unfollowed";
                })
                .orElse("⚠ Not following");
    }
}
