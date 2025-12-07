package com.v.app.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.v.app.post.PostRepository;
import com.v.app.user.UserDto;
import com.v.app.user.UserEntity;
import com.v.app.user.UserRepository;
import com.v.app.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createReport(CreateReportRequest request) {
        UserEntity currentUser = userService.getCurrentUserEntity();

        ReportEntity report = new ReportEntity();
        report.setReporter(currentUser);
        if (request.getReportedUserId() != null) {
            report.setReportedUser(userRepository.getReferenceById(request.getReportedUserId()));
        }
        if (request.getReportedPostId() != null) {
            report.setPost(postRepository.getReferenceById(request.getReportedPostId()));
        }
        report.setReason(request.getReason());
        report.setStatus("PENDING");

        reportRepository.save(report);
    }

    public Page<ReportDto> listReports(Pageable pageable) {
        // Only admin should access this. Security config should handle role check.
        return reportRepository.findAll(pageable).map(this::mapToDto);
    }

    @Transactional
    public void resolveReport(Long id, String status) {
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(status);
        reportRepository.save(report);
    }

    private ReportDto mapToDto(ReportEntity entity) {
        ReportDto dto = new ReportDto();
        dto.setId(entity.getId());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        if (entity.getPost() != null) {
            dto.setReportedPostId(entity.getPost().getId());
        }

        UserDto reporterDto = new UserDto();
        reporterDto.setId(entity.getReporter().getId());
        reporterDto.setUsername(entity.getReporter().getUsername());
        dto.setReporter(reporterDto);

        if (entity.getReportedUser() != null) {
            UserDto reportedUserDto = new UserDto();
            reportedUserDto.setId(entity.getReportedUser().getId());
            reportedUserDto.setUsername(entity.getReportedUser().getUsername());
            dto.setReportedUser(reportedUserDto);
        }

        return dto;
    }
}
