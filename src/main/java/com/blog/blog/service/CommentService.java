package com.blog.blog.service;

import com.blog.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);

    void delete(Long id);
}
