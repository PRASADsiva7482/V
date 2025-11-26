package com.v.app.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Page<PostEntity> findByReplyToPostIdOrderByCreatedAtDesc(Long replyToPostId, Pageable pageable);

    Page<PostEntity> findByUserIdAndMediaUrlIsNotNullOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // For Home Timeline (simplified: all posts from everyone for now, or use a
    // custom query for following)
    // In a real app, this would be complex. Here we can just return all posts or
    // posts from followed users.
    @Query("SELECT p FROM PostEntity p WHERE p.user.id IN :userIds OR p.user.id = :currentUserId ORDER BY p.createdAt DESC")
    Page<PostEntity> findByUserIds(@Param("userIds") List<Long> userIds, @Param("currentUserId") Long currentUserId,
            Pageable pageable);

    // Hashtag search (requires join)
    @Query("SELECT p FROM PostEntity p JOIN p.hashtags h WHERE h.tag = :tag ORDER BY p.createdAt DESC")
    Page<PostEntity> findByHashtag(@Param("tag") String tag, Pageable pageable);

    Page<PostEntity> findByContentContaining(String content, Pageable pageable);

    long countByReplyToPostId(Long postId);

    long countByQuotePostId(Long postId);
}
