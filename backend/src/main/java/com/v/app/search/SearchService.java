package com.v.app.search;

import com.v.app.post.PostDto;
import com.v.app.post.PostRepository;
import com.v.app.post.PostService;
import com.v.app.user.UserDto;
import com.v.app.user.UserRepository;
import com.v.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostService postService;

    public Page<UserDto> searchUsers(String query, Pageable pageable) {
        // Basic search: username or display name contains query
        // Need to add findByUsernameContainingOrDisplayNameContaining to UserRepository
        return userRepository.findByUsernameContainingOrDisplayNameContaining(query, query, pageable)
                .map(user -> userService.getUserProfile(user.getUsername())); // Reusing getUserProfile to map DTO
    }

    public Page<PostDto> searchPosts(String query, Pageable pageable) {
        // Basic search: content contains query
        // Need to add findByContentContaining to PostRepository
        return postRepository.findByContentContaining(query, pageable)
                .map(post -> postService.getPost(post.getId())); // Reusing getPost to map DTO
    }
}
