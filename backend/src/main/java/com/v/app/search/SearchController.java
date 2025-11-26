package com.v.app.search;

import com.v.app.common.ApiResponse;
import com.v.app.post.PostDto;
import com.v.app.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<UserDto>>> searchUsers(
            @RequestParam String query, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(searchService.searchUsers(query, pageable)));
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<Page<PostDto>>> searchPosts(
            @RequestParam String query, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(searchService.searchPosts(query, pageable)));
    }
}
