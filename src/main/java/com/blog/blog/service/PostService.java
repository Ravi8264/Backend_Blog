package com.blog.blog.service;

import com.blog.blog.payloads.PostDto;
import com.blog.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto create(PostDto postDto, Long categoryId, Long userId);

    PostDto update(PostDto postDto, Long id);

    void delete(Long id);

    PostResponse getAllPosts(int pagenumber, int pagesize, String sortBy, String sortDir);

    PostDto getById(Long id);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> getPostsByUserId(Long userId);

    List<PostDto> searchPosts(String keyword);

    boolean isPostOwner(Long postId, String userEmail);
}
