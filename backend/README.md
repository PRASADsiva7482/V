# V Backend

Spring Boot backend for V social network.

## Setup

1.  **Database**: Ensure MySQL is running and create a database named `v_db`.
    ```sql
    CREATE DATABASE v_db;
    ```
2.  **Configuration**: Update `src/main/resources/application.yml` with your database credentials if different from default.
3.  **Build**:
    ```bash
    mvn clean install
    ```
4.  **Run**:
    ```bash
    mvn spring-boot:run
    ```

## API Documentation

Once running, access Swagger UI at:
http://localhost:8080/swagger-ui.html

## Features

*   **Auth**: JWT based authentication (Signup, Login).
*   **Users**: Profile, Follow/Unfollow.
*   **Posts**: Create, Reply, Quote, Like, Repost, Bookmark.
*   **Timelines**: Home, User, Hashtag.
*   **Search**: Users and Posts.
*   **DMs**: Conversations and Messages.
*   **Notifications**: Activity feed.
*   **Admin**: Moderation/Reports.
