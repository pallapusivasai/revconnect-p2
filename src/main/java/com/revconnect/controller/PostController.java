package com.revconnect.controller;

import com.revconnect.model.User;
import com.revconnect.service.PostService;
import com.revconnect.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    public PostController(PostService postService,
                          UserRepository userRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}")
    public String createPost(@PathVariable Long userId,
                             @RequestBody String content) {

        User user = userRepository.findById(userId).orElseThrow();
        postService.createPost(user, content);
        return "Post created successfully";
    }
}
