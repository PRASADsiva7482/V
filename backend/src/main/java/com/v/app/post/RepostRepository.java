package com.v.app.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepostRepository extends JpaRepository<RepostEntity, RepostId> {
    long countByPostId(Long postId);
}
