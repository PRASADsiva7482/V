package com.v.app.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileDto {
    private String displayName;
    private String bio;
    private String location;
    private String website;
    private LocalDate birthDate;
    private String avatarUrl;
    private String bannerUrl;
}
