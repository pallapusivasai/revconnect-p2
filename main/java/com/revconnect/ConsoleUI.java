package com.revconnect;

import com.revconnect.model.User;
import com.revconnect.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleUI implements CommandLineRunner {

    @Autowired private AuthService authService;
    @Autowired private PostService postService;
    @Autowired private LikeService likeService;
    @Autowired private FollowService followService;
    @Autowired private CommentService commentService;
    @Autowired private NotificationService notificationService;

    private final Scanner sc = new Scanner(System.in);

    @Override
    public void run(String... args) {
        while (true) {
            System.out.println("\n=== RevConnect ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int ch = Integer.parseInt(sc.nextLine());
            if (ch == 1) register();
            else if (ch == 2) login();
            else System.exit(0);
        }
    }

    private void register() {
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        System.out.println(authService.register(e, p));
    }

    private void login() {
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        User u = authService.login(e, p);
        if (u == null) {
            System.out.println("❌ Login failed");
            return;
        }
        System.out.println("✅ Login successful");
        dashboard(u);
    }

    private void dashboard(User u) {
        while (true) {
            System.out.println("\n=== User Dashboard ===");
            System.out.println("Logged in as: " + u.getEmail());
            System.out.println("--------------------------------");
            System.out.println("1. Create Post");
            System.out.println("2. View My Posts");
            System.out.println("3. View Feed");
            System.out.println("4. Like Post");
            System.out.println("5. Unlike Post");
            System.out.println("6. Comment on Post");
            System.out.println("7. Follow User");
            System.out.println("8. Unfollow User");
            System.out.println("9. View Notifications");
            System.out.println("10. Logout");
            System.out.print("Enter choice: ");
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1 -> System.out.println(postService.createPost(u, sc));
                case 2 -> postService.viewMyPosts(u);
                case 3 -> postService.viewFeed();
                case 4 -> System.out.println(likeService.like(u, sc));
                case 5 -> System.out.println(likeService.unlike(u, sc));
                case 6 -> System.out.println(commentService.comment(u, sc));
                case 7 -> System.out.println(followService.follow(u, sc));
                case 8 -> System.out.println(followService.unfollow(u, sc));
                case 9 -> notificationService.view(u);
                case 10 -> { return; }
            }
        }
    }
}
