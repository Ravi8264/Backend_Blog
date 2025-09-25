package com.blog.blog.service;

import com.blog.blog.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId, Long userId);

    void delete(Long id);

    List<CommentDto> getCommentsByPostId(Long postId);
}
