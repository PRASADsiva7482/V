package com.v.app.user;

import com.v.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        return ResponseEntity.ok(ApiResponse.success(userService.getCurrentUser()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserDto>> getUserProfile(@PathVariable String username) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserProfile(username)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(@RequestBody UpdateProfileDto request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateProfile(request)));
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<ApiResponse<Void>> followUser(@PathVariable Long id) {
        userService.followUser(id);
        return ResponseEntity.ok(ApiResponse.success("Followed user", null));
    }

    @DeleteMapping("/{id}/follow")
    public ResponseEntity<ApiResponse<Void>> unfollowUser(@PathVariable Long id) {
        userService.unfollowUser(id);
        return ResponseEntity.ok(ApiResponse.success("Unfollowed user", null));
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<ApiResponse<List<UserDto>>> getFollowers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getFollowers(id)));
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<ApiResponse<List<UserDto>>> getFollowing(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getFollowing(id)));
    }
}
