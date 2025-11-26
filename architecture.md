# Project V - Architecture & Structure

## Overview
"V" is a Twitter-like social media application for Android, backed by a Spring Boot REST API and MySQL database.

## Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.x (Java 17)
- **Pattern**: Layered Architecture (Controller -> Service -> Repository -> Database)
- **Security**: Spring Security with JWT (Stateless)
- **Database**: MySQL 8.0
- **Build Tool**: Maven
- **Key Modules**:
    - **Auth**: Registration, Login, JWT issuance/refresh.
    - **User**: Profiles, Follow/Unfollow logic.
    - **Post**: Creating posts, replies, reposts, likes, bookmarks.
    - **Timeline**: Aggregating feeds (Home, User, Tag).
    - **Search**: Finding users and posts.
    - **DM**: Direct messaging (1-on-1).
    - **Notification**: Event tracking (likes, mentions, etc.).
    - **Admin**: Moderation actions.

### Android App (Kotlin)
- **UI Toolkit**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Clean Architecture
    - **Data Layer**: Repositories, Retrofit (API), Room (optional cache), DataStore (Auth token).
    - **Domain Layer**: Use Cases (pure Kotlin logic).
    - **UI Layer**: ViewModels, Composable Screens.
- **Dependency Injection**: Hilt
- **Async**: Coroutines & Flow
- **Navigation**: Jetpack Navigation Compose

### Database
- **Type**: Relational (MySQL)
- **Management**: `schema.sql` for initialization.

## Folder Structure

### Root
```
/
├── backend/                # Spring Boot Backend
├── android-app/            # Android Client
├── db/                     # Database Scripts
└── README.md               # Project Documentation
```

### Backend Structure (`backend/`)
```
backend/
├── src/main/java/com/v/app/
│   ├── config/             # SecurityConfig, WebConfig, SwaggerConfig
│   ├── common/             # GlobalExceptionHandler, ApiResponse, BaseEntity
│   ├── auth/               # AuthController, AuthService, AuthRequest/Response
│   ├── user/               # UserController, UserService, UserEntity, UserRepository
│   ├── post/               # PostController, PostService, PostEntity, PostRepository
│   ├── dm/                 # DMController, DMService, MessageEntity
│   ├── notification/       # NotificationController, NotificationService
│   ├── search/             # SearchController, SearchService
│   ├── admin/              # AdminController, AdminService
│   └── VApplication.java   # Main Entry Point
└── pom.xml
```

### Android Structure (`android-app/`)
```
android-app/
├── app/src/main/java/com/v/app/
│   ├── data/
│   │   ├── api/            # Retrofit Interfaces (AuthApi, PostApi, etc.)
│   │   ├── model/          # DTOs (Network Models)
│   │   └── repository/     # Repository Implementations (AuthRepositoryImpl, etc.)
│   ├── domain/
│   │   ├── model/          # Domain Models (User, Post, etc.)
│   │   ├── repository/     # Repository Interfaces
│   │   └── usecase/        # LoginUseCase, CreatePostUseCase, etc.
│   ├── ui/
│   │   ├── theme/          # Color, Type, Theme
│   │   ├── components/     # Reusable Composables (PostItem, Avatar, etc.)
│   │   ├── screens/
│   │   │   ├── auth/       # LoginScreen, SignupScreen
│   │   │   ├── home/       # HomeTimelineScreen
│   │   │   ├── post/       # CreatePostScreen, PostDetailScreen
│   │   │   ├── profile/    # ProfileScreen
│   │   │   ├── search/     # SearchScreen
│   │   │   ├── dm/         # DMListScreen, ChatScreen
│   │   │   └── notification/ # NotificationScreen
│   │   └── navigation/     # NavGraph, ScreenDestinations
│   └── VApp.kt             # Application Class (Hilt)
└── build.gradle.kts
```

### Database (`db/`)
```
db/
└── schema.sql              # CREATE TABLE statements and initial seed data
```
