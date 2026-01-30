package com.revconnect;

import com.revconnect.exception.RevConnectException;
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

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            sc.close();
            logger.info("Console UI stopped");
        }));

        while (true) {
            printMainMenu();

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> {
                        logger.info("Application exited by user");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice");
                }

            } catch (RevConnectException ex) {
                System.out.println("❌ " + ex.getMessage());
                logger.warn("Business error: {}", ex.getMessage());
            } catch (Exception ex) {
                System.out.println("❌ Invalid input");
                logger.error("Unexpected error", ex);
            }
        }
    }

    /* ===================== MAIN MENU ===================== */

    private void printMainMenu() {
        System.out.println("\n=== RevConnect ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    private void register() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        authService.register(email, password);
        System.out.println("✅ Registered successfully");
    }

    private void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = authService.login(email, password);
        System.out.println("✅ Login successful");

        dashboard(user);
    }

    /* ===================== DASHBOARD ===================== */

    private void dashboard(User user) {

        logger.info("User entered dashboard: {}", user.getEmail());

        while (true) {
            printDashboardMenu(user);

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1 -> {
                        System.out.print("Enter post content: ");
                        String content = sc.nextLine();
                        postService.createPost(user, content);
                        System.out.println("✅ Post created");
                    }

                    case 2 ->
                            postService.viewMyPosts(user)
                                    .forEach(p ->
                                            System.out.println("📝 " + p.getContent()));

                    case 3 ->
                            postService.viewFeed()
                                    .forEach(p ->
                                            System.out.println("📰 " + p.getContent()));

                    case 4 -> {
                        Long postId = readLong("Enter Post ID to like: ");
                        likeService.like(user, postId);
                        System.out.println("❤️ Post liked");
                    }

                    case 5 -> {
                        Long postId = readLong("Enter Post ID to unlike: ");
                        likeService.unlike(user, postId);
                        System.out.println("💔 Post unliked");
                    }

                    case 6 -> {
                        Long postId = readLong("Enter Post ID: ");
                        System.out.print("Enter comment: ");
                        String text = sc.nextLine();
                        commentService.comment(user, postId, text);
                        System.out.println("💬 Comment added");
                    }

                    case 7 -> {
                        Long userId = readLong("Enter User ID to follow: ");
                        followService.follow(user, userId);
                        System.out.println("➕ Followed user");
                    }

                    case 8 -> {
                        Long userId = readLong("Enter User ID to unfollow: ");
                        followService.unfollow(user, userId);
                        System.out.println("➖ Unfollowed user");
                    }

                    case 9 -> {
                        System.out.println("\n=== Notifications ===");
                        var notifications = notificationService.view(user);

                        if (notifications.isEmpty()) {
                            System.out.println("📭 No new notifications");
                        } else {
                            notifications.forEach(
                                    n -> System.out.println("🔔 " + n)
                            );
                        }
                    }

                    case 10 -> {
                        logger.info("User logged out: {}", user.getEmail());
                        return;
                    }

                    default -> System.out.println("❌ Invalid choice");
                }

            } catch (RevConnectException ex) {
                System.out.println("❌ " + ex.getMessage());
                logger.warn("Business error: {}", ex.getMessage());
            } catch (Exception ex) {
                System.out.println("❌ Invalid input");
                logger.error("Unexpected error", ex);
            }
        }
    }

    private void printDashboardMenu(User user) {
        System.out.println("\n=== User Dashboard ===");
        System.out.println("Logged in as: " + user.getEmail());
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
    }

    private Long readLong(String prompt) {
        System.out.print(prompt);
        return Long.parseLong(sc.nextLine());
    }
}
