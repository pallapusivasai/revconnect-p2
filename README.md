# RevConnect – Console Based Social Media Application

RevConnect is a console-based social media application developed using Spring Boot, Spring Data JPA, Maven, and MySQL.
The application allows users to interact through posts, likes, comments, follows, and notifications using a
command-line interface (no web UI).

This project was originally built using Core Java and JDBC and later refactored into a clean Spring Boot + JPA
architecture with proper layering and automated unit tests.

--------------------------------------------------------------------

## Features

User Management
- User registration
- User login and logout

Posts
- Create a post
- View my posts
- View feed (all users’ posts)

Likes
- Like a post
- Unlike a post
- Prevent duplicate likes

Comments
- Comment on posts
- Notify post owner when a comment is added

Follow System
- Follow a user
- Unfollow a user

Notifications
- Notifications for likes, comments, and follows
- Seen / unseen notification support
- View notifications from user dashboard

Testing
- Automated unit tests using JUnit 5
- Tests run without console input
- Separate test profile configuration

--------------------------------------------------------------------

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- JUnit 5
- IntelliJ IDEA
- Git & GitHub

--------------------------------------------------------------------

## Project Structure

revconnect
│
├── pom.xml
├── README.md
├── .gitignore
└── src
├── main
│   ├── java
│   │   └── com.revconnect
│   │       ├── model
│   │       ├── repository
│   │       ├── service
│   │       ├── ConsoleUI.java
│   │       └── RevConnectApplication.java
│   └── resources
│       └── application.properties
└── test
├── java
│   └── com.revconnect
│       ├── AuthServiceTest.java
│       ├── PostServiceTest.java
│       ├── LikeServiceTest.java
│       └── NotificationServiceTest.java
└── resources
└── application.properties

--------------------------------------------------------------------

## Database Setup

Create database in MySQL:

CREATE DATABASE revconnect_p2;

application.properties configuration:

spring.datasource.url=jdbc:mysql://localhost:3306/revconnect_p2
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

spring.main.web-application-type=none
logging.level.root=OFF

Tables are created automatically by Hibernate.

--------------------------------------------------------------------

## How to Run the Application

Using Maven:
mvn spring-boot:run

Using IntelliJ:
- Open RevConnectApplication.java
- Click Run

--------------------------------------------------------------------

## Sample Console Output

=== RevConnect ===
1. Register
2. Login
3. Exit

=== User Dashboard ===
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

--------------------------------------------------------------------

## Running Tests

mvn test

- Fully automated
- No console input required
- Uses test Spring profile

--------------------------------------------------------------------

## Key Concepts Demonstrated

- Console-based Spring Boot application
- Layered architecture (UI, Service, Repository)
- JPA entity relationships (OneToMany, ManyToOne)
- Separation of console input and business logic
- Preventing duplicate actions (likes, follows)
- Notification handling
- Automated unit testing

--------------------------------------------------------------------

## Future Enhancements

- Password encryption
- Pagination for feed
- REST API version
- Web-based UI

--------------------------------------------------------------------

## Author

Palla Siva Sai
Java | Spring Boot | Backend Development
