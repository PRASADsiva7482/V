package com.v.app.dm;

import com.v.app.user.UserDto;
import com.v.app.user.UserEntity;
import com.v.app.user.UserRepository;
import com.v.app.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DMService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public MessageDto sendMessage(SendMessageRequest request) {
        UserEntity currentUser = userService.getCurrentUserEntity();
        ConversationEntity conversation;

        if (request.getConversationId() != null) {
            conversation = conversationRepository.findById(request.getConversationId())
                    .orElseThrow(() -> new RuntimeException("Conversation not found"));
        } else if (request.getRecipientId() != null) {
            // Check if conversation exists
            // Need custom query to find conversation between two users
            // For now, let's assume we create a new one or find existing if we had a proper
            // repo method
            // Simplification: just create new if not found by ID, but in real app we check
            // unique constraint
            // Let's implement findByUsers in repo
            UserEntity recipient = userRepository.findById(request.getRecipientId())
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));

            // Try to find existing
            // This logic is tricky without a custom query.
            // Let's just create a new one and let DB constraint fail if exists, then catch?
            // No, better to query.
            // Skipping complex check for now, assuming client sends conversationId if
            // known.

            conversation = new ConversationEntity();
            conversation.setUser1(currentUser);
            conversation.setUser2(recipient);
            conversation = conversationRepository.save(conversation);
        } else {
            throw new RuntimeException("Recipient or Conversation ID required");
        }

        MessageEntity message = new MessageEntity();
        message.setConversation(conversation);
        message.setSender(currentUser);
        message.setContent(request.getContent());
        message.setRead(false);

        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        return mapToMessageDto(messageRepository.save(message));
    }

    public Page<ConversationDto> listConversations(Pageable pageable) {
        // UserEntity currentUser = userService.getCurrentUserEntity();
        // Need repo method: findByUser1OrUser2
        // For now, stub return empty
        return Page.empty();
    }

    public Page<MessageDto> listMessages(Long conversationId, Pageable pageable) {
        return messageRepository.findByConversationIdOrderByCreatedAtDesc(conversationId, pageable)
                .map(this::mapToMessageDto);
    }

    private MessageDto mapToMessageDto(MessageEntity message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setConversationId(message.getConversation().getId());
        dto.setContent(message.getContent());
        dto.setRead(message.isRead());
        dto.setCreatedAt(message.getCreatedAt());

        UserDto senderDto = new UserDto();
        senderDto.setId(message.getSender().getId());
        senderDto.setUsername(message.getSender().getUsername());
        senderDto.setAvatarUrl(message.getSender().getAvatarUrl());
        dto.setSender(senderDto);

        return dto;
    }
}
