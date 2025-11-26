package com.v.app.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkId implements Serializable {
    private Long userId;
    private Long postId;
}
