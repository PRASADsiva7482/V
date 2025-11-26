package com.v.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, FollowId> {
    long countByFollowerId(Long followerId);

    long countByFollowingId(Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<FollowEntity> findByFollowerId(Long followerId);

    List<FollowEntity> findByFollowingId(Long followingId);
}
