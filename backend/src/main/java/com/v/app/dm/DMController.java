package com.v.app.dm;

import com.v.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dms")
@RequiredArgsConstructor
public class DMController {

    private final DMService dmService;

    @PostMapping("/messages")
    public ResponseEntity<ApiResponse<MessageDto>> sendMessage(@RequestBody SendMessageRequest request) {
        return ResponseEntity.ok(ApiResponse.success(dmService.sendMessage(request)));
    }

    @GetMapping("/conversations")
    public ResponseEntity<ApiResponse<Page<ConversationDto>>> listConversations(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(dmService.listConversations(pageable)));
    }

    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<ApiResponse<Page<MessageDto>>> listMessages(
            @PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(dmService.listMessages(id, pageable)));
    }
}
