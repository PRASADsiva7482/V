package com.v.app.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public UserEntity getCurrentUserEntity() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDto getCurrentUser() {
        return mapToDto(getCurrentUserEntity());
    }

    public UserDto getUserProfile(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    @Transactional
    public UserDto updateProfile(UpdateProfileDto request) {
        UserEntity user = getCurrentUserEntity();
        if (request.getDisplayName() != null)
            user.setDisplayName(request.getDisplayName());
        if (request.getBio() != null)
            user.setBio(request.getBio());
        if (request.getLocation() != null)
            user.setLocation(request.getLocation());
        if (request.getWebsite() != null)
            user.setWebsite(request.getWebsite());
        if (request.getBirthDate() != null)
            user.setBirthDate(request.getBirthDate());
        if (request.getAvatarUrl() != null)
            user.setAvatarUrl(request.getAvatarUrl());
        if (request.getBannerUrl() != null)
            user.setBannerUrl(request.getBannerUrl());

        return mapToDto(userRepository.save(user));
    }

    @Transactional
    public void followUser(Long targetUserId) {
        UserEntity currentUser = getCurrentUserEntity();
        if (currentUser.getId().equals(targetUserId)) {
            throw new RuntimeException("Cannot follow yourself");
        }
        if (!userRepository.existsById(targetUserId)) {
            throw new RuntimeException("User not found");
        }
        if (followRepository.existsByFollowerIdAndFollowingId(currentUser.getId(), targetUserId)) {
            return; // Already following
        }

        FollowEntity follow = new FollowEntity();
        follow.setFollowerId(currentUser.getId());
        follow.setFollowingId(targetUserId);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(Long targetUserId) {
        UserEntity currentUser = getCurrentUserEntity();
        FollowId id = new FollowId(currentUser.getId(), targetUserId);
        if (followRepository.existsById(id)) {
            followRepository.deleteById(id);
        }
    }

    public List<UserDto> getFollowers(Long userId) {
        return followRepository.findByFollowingId(userId).stream()
                .map(FollowEntity::getFollower)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getFollowing(Long userId) {
        return followRepository.findByFollowerId(userId).stream()
                .map(FollowEntity::getFollowing)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private UserDto mapToDto(UserEntity user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        dto.setBio(user.getBio());
        dto.setLocation(user.getLocation());
        dto.setWebsite(user.getWebsite());
        dto.setBirthDate(user.getBirthDate());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setBannerUrl(user.getBannerUrl());
        dto.setVerified(user.isVerified());
        dto.setCreatedAt(user.getCreatedAt());

        dto.setFollowersCount(followRepository.countByFollowingId(user.getId()));
        dto.setFollowingCount(followRepository.countByFollowerId(user.getId()));

        // Contextual check if current user is following this user
        try {
            UserEntity currentUser = getCurrentUserEntity();
            dto.setFollowing(followRepository.existsByFollowerIdAndFollowingId(currentUser.getId(), user.getId()));
        } catch (Exception e) {
            dto.setFollowing(false);
        }

        return dto;
    }
}
