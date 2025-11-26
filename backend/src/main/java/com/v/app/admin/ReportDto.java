package com.v.app.admin;

import com.v.app.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Long id;
    private UserDto reporter;
    private UserDto reportedUser;
    private Long reportedPostId;
    private String reason;
    private String status;
    private LocalDateTime createdAt;
}
