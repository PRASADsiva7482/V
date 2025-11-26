package com.v.app.post;

import com.v.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(@RequestBody CreatePostRequest request) {
        return ResponseEntity.ok(ApiResponse.success(postService.createPost(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(postService.getPost(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ApiResponse.success("Post deleted", null));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ApiResponse<Void>> likePost(@PathVariable Long id) {
        postService.likePost(id);
        return ResponseEntity.ok(ApiResponse.success("Liked", null));
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<ApiResponse<Void>> unlikePost(@PathVariable Long id) {
        postService.unlikePost(id);
        return ResponseEntity.ok(ApiResponse.success("Unliked", null));
    }

    @PostMapping("/{id}/repost")
    public ResponseEntity<ApiResponse<Void>> repost(@PathVariable Long id) {
        postService.repost(id);
        return ResponseEntity.ok(ApiResponse.success("Reposted", null));
    }

    @DeleteMapping("/{id}/repost")
    public ResponseEntity<ApiResponse<Void>> unrepost(@PathVariable Long id) {
        postService.unrepost(id);
        return ResponseEntity.ok(ApiResponse.success("Unreposted", null));
    }

    @PostMapping("/{id}/bookmark")
    public ResponseEntity<ApiResponse<Void>> bookmark(@PathVariable Long id) {
        postService.bookmark(id);
        return ResponseEntity.ok(ApiResponse.success("Bookmarked", null));
    }

    @DeleteMapping("/{id}/bookmark")
    public ResponseEntity<ApiResponse<Void>> unbookmark(@PathVariable Long id) {
        postService.unbookmark(id);
        return ResponseEntity.ok(ApiResponse.success("Unbookmarked", null));
    }

    @GetMapping("/timeline/home")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getHomeTimeline(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(postService.getHomeTimeline(pageable)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getUserTimeline(
            @PathVariable Long userId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(postService.getUserTimeline(userId, pageable)));
    }

    @GetMapping("/hashtag/{tag}")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getHashtagPosts(
            @PathVariable String tag,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(postService.getHashtagPosts(tag, pageable)));
    }
}
