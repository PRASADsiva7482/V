package com.v.app.admin;

import com.v.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/reports")
    public ResponseEntity<ApiResponse<Void>> createReport(@RequestBody CreateReportRequest request) {
        adminService.createReport(request);
        return ResponseEntity.ok(ApiResponse.success("Report submitted", null));
    }

    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<Page<ReportDto>>> listReports(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(adminService.listReports(pageable)));
    }

    @PutMapping("/reports/{id}")
    public ResponseEntity<ApiResponse<Void>> resolveReport(@PathVariable Long id, @RequestParam String status) {
        adminService.resolveReport(id, status);
        return ResponseEntity.ok(ApiResponse.success("Report resolved", null));
    }
}
