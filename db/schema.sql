-- ==========================================
-- Schema for "V" (Twitter-like App)
-- Database: MySQL
-- ==========================================

-- 1. Users & Profiles
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE, -- Handle (e.g. @john)
    display_name VARCHAR(100) NOT NULL,
    bio VARCHAR(160),
    location VARCHAR(100),
    website VARCHAR(255),
    birth_date DATE,
    avatar_url VARCHAR(255),
    banner_url VARCHAR(255),
    is_verified BOOLEAN DEFAULT FALSE,
    is_suspended BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_users_username (username),
    INDEX idx_users_email (email)
);

-- 2. Follows
CREATE TABLE follows (
    follower_id BIGINT NOT NULL,
    following_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, following_id),
    FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_follows_follower (follower_id),
    INDEX idx_follows_following (following_id)
);

-- 3. Posts (Vees)
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content VARCHAR(500), -- Text content
    media_url VARCHAR(255), -- Simple single media URL for now
    reply_to_post_id BIGINT, -- If this is a reply
    quote_post_id BIGINT, -- If this is a quote repost
    is_deleted BOOLEAN DEFAULT FALSE, -- Soft delete
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (reply_to_post_id) REFERENCES posts(id) ON DELETE SET NULL,
    FOREIGN KEY (quote_post_id) REFERENCES posts(id) ON DELETE SET NULL,
    INDEX idx_posts_user (user_id),
    INDEX idx_posts_reply (reply_to_post_id),
    INDEX idx_posts_created_at (created_at) -- For timeline sorting
);

-- 4. Interactions: Likes
CREATE TABLE likes (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    INDEX idx_likes_post (post_id)
);

-- 5. Interactions: Reposts (Retweets)
CREATE TABLE reposts (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    INDEX idx_reposts_post (post_id)
);

-- 6. Bookmarks
CREATE TABLE bookmarks (
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

-- 7. Hashtags
CREATE TABLE hashtags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tag VARCHAR(100) NOT NULL UNIQUE,
    last_used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_hashtags_tag (tag)
);

CREATE TABLE post_hashtags (
    post_id BIGINT NOT NULL,
    hashtag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, hashtag_id),
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (hashtag_id) REFERENCES hashtags(id) ON DELETE CASCADE
);

-- 8. Direct Messages (Conversations & Messages)
-- Simple approach: A message belongs to a sender and receiver.
-- Grouping into "Conversations" can be done logically or via a conversation_id.
-- Let's use a simple conversation table to track last message/update.
CREATE TABLE conversations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    last_message_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_conversation (user1_id, user2_id),
    FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    conversation_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (conversation_id) REFERENCES conversations(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_messages_conversation (conversation_id)
);

-- 9. Notifications
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient_id BIGINT NOT NULL,
    actor_id BIGINT, -- User who triggered the notification
    type VARCHAR(20) NOT NULL, -- FOLLOW, LIKE, REPLY, REPOST, MENTION, DM
    entity_id BIGINT, -- ID of the post, message, etc.
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (actor_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_notifications_recipient (recipient_id)
);

-- 10. Admin / Reports
CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, RESOLVED, DISMISSED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

-- ==========================================
-- Sample Data (Seeds)
-- ==========================================

-- Users (Passwords are 'password' hashed with BCrypt for example purposes, 
-- but here we just put a placeholder string as the backend will handle hashing in real app.
-- For seed, let's assume a dummy hash or plain text if dev mode allows, 
-- but better to put a known hash. 
-- $2a$10$DkV.d.W8X5.L.W8X5.L.W8X5.L.W8X5.L.W8X5.L.W8X5.L.W8X5 (dummy)
INSERT INTO users (email, password_hash, username, display_name, bio, is_verified) VALUES
('alice@example.com', '$2a$10$ExampleHashForPassword123', 'alice', 'Alice Wonderland', 'Curiouser and curiouser.', TRUE),
('bob@example.com', '$2a$10$ExampleHashForPassword123', 'bob', 'Bob Builder', 'Can we fix it?', FALSE),
('charlie@example.com', '$2a$10$ExampleHashForPassword123', 'charlie', 'Charlie Chef', 'Cooking up a storm.', FALSE),
('admin@v.app', '$2a$10$ExampleHashForPassword123', 'admin', 'V Admin', 'Official Admin Account', TRUE);

-- Follows
INSERT INTO follows (follower_id, following_id) VALUES
(1, 2), -- Alice follows Bob
(1, 3), -- Alice follows Charlie
(2, 1), -- Bob follows Alice
(3, 1); -- Charlie follows Alice

-- Posts
INSERT INTO posts (user_id, content, created_at) VALUES
(1, 'Hello world! This is my first Vee on V.', NOW() - INTERVAL 2 DAY),
(2, 'Building something cool today. #coding #builder', NOW() - INTERVAL 1 DAY),
(3, 'Anyone want a burger? 🍔', NOW() - INTERVAL 5 HOUR);

-- Replies
INSERT INTO posts (user_id, content, reply_to_post_id, created_at) VALUES
(1, 'Yes please, Bob!', 2, NOW() - INTERVAL 20 HOUR), -- Alice replies to Bob
(2, 'I am hungry too.', 3, NOW() - INTERVAL 4 HOUR); -- Bob replies to Charlie

-- Likes
INSERT INTO likes (user_id, post_id) VALUES
(1, 2), -- Alice likes Bob's post
(3, 1), -- Charlie likes Alice's post
(2, 3); -- Bob likes Charlie's post

-- Hashtags
INSERT INTO hashtags (tag) VALUES ('coding'), ('builder');

-- Post Hashtags
INSERT INTO post_hashtags (post_id, hashtag_id) VALUES
(2, 1), -- Bob's post -> #coding
(2, 2); -- Bob's post -> #builder

-- DMs
INSERT INTO conversations (user1_id, user2_id) VALUES (1, 2); -- Alice & Bob
INSERT INTO messages (conversation_id, sender_id, content) VALUES
(1, 1, 'Hey Bob, how is the building going?'),
(1, 2, 'Going great Alice! Almost done.');

-- Notifications
INSERT INTO notifications (recipient_id, actor_id, type, entity_id) VALUES
(2, 1, 'FOLLOW', NULL), -- Alice followed Bob
(2, 1, 'LIKE', 2), -- Alice liked Bob's post
(2, 1, 'REPLY', 4); -- Alice replied to Bob's post
