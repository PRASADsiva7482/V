$path = "d:\Project\V\db\schema.sql"
$content = Get-Content -Path $path -Raw

# Define the block to replace. Note: The indentation must match exactly.
$oldBlock = "CREATE TABLE reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, RESOLVED, DISMISSED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);"

$newBlock = "CREATE TABLE reports (
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
);"

# Try to replace. We replace CRLF with LF for comparison to be safe, or just try direct replace.
# Let's try direct replace first.
if ($content.Contains($oldBlock)) {
    $newContent = $content.Replace($oldBlock, $newBlock)
    Set-Content -Path $path -Value $newContent -NoNewline
    Write-Host "Successfully updated schema.sql"
} else {
    # Try normalizing line endings to just LF
    $contentLF = $content -replace "`r`n", "`n"
    $oldBlockLF = $oldBlock -replace "`r`n", "`n"
    $newBlockLF = $newBlock -replace "`r`n", "`n"
    
    if ($contentLF.Contains($oldBlockLF)) {
        $newContent = $contentLF.Replace($oldBlockLF, $newBlockLF)
        # Write back. Note: this might change all line endings to LF. Windows usually accepts that.
        Set-Content -Path $path -Value $newContent -NoNewline
        Write-Host "Successfully updated schema.sql (normalized)"
    } else {
        Write-Host "Could not find the target block in schema.sql"
        $index = $content.IndexOf("CREATE TABLE reports")
        if ($index -ge 0) {
             Write-Host "Found 'CREATE TABLE reports' at index $index"
             Write-Host "Snippet:"
             Write-Host $content.Substring($index, 400)
        }
    }
}
