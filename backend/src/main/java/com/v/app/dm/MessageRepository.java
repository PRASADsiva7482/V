package com.v.app.dm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    org.springframework.data.domain.Page<MessageEntity> findByConversationIdOrderByCreatedAtDesc(Long conversationId,
            org.springframework.data.domain.Pageable pageable);
}
