package com.revconnect;

import com.revconnect.model.User;
import com.revconnect.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("!test")
public class ConsoleUI implements CommandLineRunner {

    private static final Logger logger =
            LogManager.getLogger(ConsoleUI.class);

    private final AuthService authService;
    private final PostService postService;
    private final LikeService likeService;
    private final FollowService followService;
    private final CommentService commentService;
    private final NotificationService notificationService;

    private final Scanner sc = new Scanner(System.in);

    // ✅ Constructor Injection (BEST PRACTICE)
    public ConsoleUI(
            AuthService authService,
            PostService postService,
            LikeService likeService,
            FollowService followService,
            CommentService commentService,
            NotificationService notificationService
    ) {
        this.authService = authService;
        this.postService = postService;
        this.likeService = likeService;
        this.followService = followService;
        this.commentService = commentService;
        this.notificationService = notificationService;
    }

    @Override
    public void run(String... args) {

        logger.info("Console UI started");

        while (true) {
            System.out.println("\n=== RevConnect ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) register();
            else if (ch == 2) login();
            else {
                logger.info("Application exited by user");
                System.exit(0);
            }
        }
    }

    private void register() {
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        logger.debug("Register menu selected");
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

        logger.info("User entered dashboard: {}", u.getEmail());

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

                case 1 -> {
                    System.out.print("Enter post content: ");
                    String content = sc.nextLine();
                    System.out.println(postService.createPost(u, content));
                }

                case 2 -> postService.viewMyPosts(u);

                case 3 -> postService.viewFeed();

                case 4 -> {
                    System.out.print("Enter Post ID to like: ");
                    Long postId = Long.parseLong(sc.nextLine());

                    String result = likeService.like(u, postId);
                    System.out.println(result);   // ✅ THIS LINE WAS MISSING
                }


                case 5 -> {
                    System.out.print("Enter Post ID to unlike: ");
                    Long postId = Long.parseLong(sc.nextLine());
                    System.out.println(likeService.unlike(u, postId));
                }

                case 6 -> {
                    System.out.print("Enter Post ID: ");
                    Long postId = Long.parseLong(sc.nextLine());
                    System.out.print("Enter comment: ");
                    String text = sc.nextLine();
                    System.out.println(commentService.comment(u, postId, text));
                }

                case 7 -> {
                    System.out.print("Enter User ID to follow: ");
                    Long userId = Long.parseLong(sc.nextLine());
                    System.out.println(followService.follow(u, userId));
                }

                case 8 -> {
                    System.out.print("Enter User ID to unfollow: ");
                    Long userId = Long.parseLong(sc.nextLine());
                    System.out.println(followService.unfollow(u, userId));
                }

                case 9 -> {
                    var messages = notificationService.view(u);

                    System.out.println("\n=== Notifications ===");

                    if (messages.isEmpty()) {
                        System.out.println("📭 No new notifications");
                    } else {
                        messages.forEach(m -> System.out.println("🔔 " + m));
                    }
                }


                case 10 -> {
                    logger.info("User logged out: {}", u.getEmail());
                    return;
                }

                default -> System.out.println("❌ Invalid choice");
            }
        }
    }
}
