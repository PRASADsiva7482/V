import os

path = r"d:\Project\V\db\schema.sql"

try:
    with open(path, "r", encoding="utf-8") as f:
        content = f.read()

    # The exact block to replace, based on the `type` output
    old_block = """CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, RESOLVED, DISMISSED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);"""

    # The new block with reported_user_id and nullable post_id
    new_block = """CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id BIGINT NOT NULL,
    post_id BIGINT,
    reported_user_id BIGINT,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, RESOLVED, DISMISSED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (reported_user_id) REFERENCES users(id) ON DELETE CASCADE
);"""

    # Normalize line endings just in case
    content_normalized = content.replace("\r\n", "\n")
    old_block_normalized = old_block.replace("\r\n", "\n")
    
    if old_block_normalized in content_normalized:
        new_content = content_normalized.replace(old_block_normalized, new_block)
        with open(path, "w", encoding="utf-8") as f:
            f.write(new_content)
        print("Successfully updated schema.sql")
    else:
        print("Could not find the target block in schema.sql")
        # Debugging: print the section where reports table is expected
        start_index = content_normalized.find("CREATE TABLE reports")
        if start_index != -1:
            print("Found 'CREATE TABLE reports' at index", start_index)
            print("Next 500 chars:")
            print(content_normalized[start_index:start_index+500])
        else:
            print("'CREATE TABLE reports' not found.")

except Exception as e:
    print(f"Error: {e}")
