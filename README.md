# RevConnect – Console-Based Social Media Application

RevConnect is a console-based social media application developed using Java, Spring Boot, Spring Data JPA, and MySQL. It simulates core social networking features such as user authentication, posts, likes, comments, following users, and notifications through a command-line interface (CLI). This project demonstrates backend development concepts, clean layered architecture, database relationships, and real-world business logic.

FEATURES
- User registration and login with duplicate prevention
- Create posts and view personal or global feed
- Like and unlike posts with duplicate-like prevention
- Comment on posts
- Follow and unfollow users with duplicate prevention
- Notifications for likes, comments, and new followers
- Seen and unseen notification tracking

TECHNOLOGY STACK
- Java
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Maven
- JUnit 5
- Log4j
- IntelliJ IDEA
- Git and GitHub

PROJECT STRUCTURE
revconnect-p2
src/
├── main/java/com/revconnect
│    ├── model           → Entities (User, Post, Like, Comment, Notification)
│    ├── repository      → JPA repos for DB access
│    ├── service         → Business logic
│    ├── ConsoleUI.java  → CLI menu & input handling
│    └── RevConnectApplication.java (Spring Boot entry point)
└── test/java/com/revconnect
→ Service tests (Auth, Post, Like, Notification)

ARCHITECTURE OVERVIEW
The application follows a layered architecture:
Console UI → Service Layer → Repository Layer → MySQL Database
- Model layer defines database entities
- Repository layer handles database operations
- Service layer contains core business logic
- Console UI manages user input and output

HOW THE APPLICATION WORKS
After starting the application, users interact through a menu-driven console interface.

Main Menu
1. Register
2. Login
3. Exit

User Dashboard
1. Create Post
2. View My Posts
3. View Feed
4. Like Post
5. Unlike Post
6. Comment on Post
7. Follow User
8. Unfollow User
9. View Notifications
10. Logout

All actions are processed in real time and persisted in the database.

TESTING
- Unit tests are written using JUnit 5
- Service-layer logic is tested independently
- Tests run without requiring console input

SETUP AND RUN INSTRUCTIONS
Prerequisites:
- Java 17
- Maven
- MySQL
- IntelliJ IDEA (recommended)

Steps:
1. Clone the repository:
   git clone https://github.com/pallapusivasai/revconnect-p2.git
2. Create a MySQL database:
   CREATE DATABASE revconnect;
3. Update application.properties with database credentials
4. Run the application:
   mvn spring-boot:run
5. Use the console menu to interact with the application

DATABASE ENTITIES
- User
- Post
- Comment
- Like
- Follow
- Notification

Entity relationships are implemented using JPA annotations such as @OneToMany, @ManyToOne, and @JoinColumn.

KEY CONCEPTS DEMONSTRATED
- Spring Boot application structure
- JPA and Hibernate ORM
- Entity relationships
- Separation of concerns
- Duplicate action prevention
- Console-based UI handling
- Logging using Log4j
- Unit testing with JUnit

FUTURE ENHANCEMENTS
- Password encryption using BCrypt
- REST API version of the application
- Web-based UI using React or Angular
- Pagination for posts and notifications
- JWT-based authentication


AUTHOR
Siva Sai Pallapu
Andhra Pradesh, India
Email: pallapu.sivasai.33@gmail.com
GitHub: https://github.com/pallapusivasai

LICENSE
This project is created for learning and demonstration purposes.
