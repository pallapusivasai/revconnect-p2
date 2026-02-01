package com.revconnect.controller;

import com.revconnect.model.Post;
import com.revconnect.model.User;
import com.revconnect.service.LikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public String likePost(@RequestBody Post post, @RequestBody User user) {
        likeService.likePost(post, user);
        return "Post liked";
    }
}
