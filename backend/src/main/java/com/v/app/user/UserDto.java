package com.v.app.user;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String displayName;
    private String bio;
    private String location;
    private String website;
    private LocalDate birthDate;
    private String avatarUrl;
    private String bannerUrl;
    private boolean isVerified;
    private LocalDateTime createdAt;

    // Counts
    private long followersCount;
    private long followingCount;
    private boolean isFollowing; // Contextual: is the current user following this user?
}
