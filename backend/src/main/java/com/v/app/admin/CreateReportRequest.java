package com.v.app.admin;

import lombok.Data;

@Data
public class CreateReportRequest {
    private Long reportedUserId;
    private Long reportedPostId;
    private String reason;
}
