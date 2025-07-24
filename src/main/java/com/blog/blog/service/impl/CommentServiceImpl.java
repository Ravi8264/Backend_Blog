package com.blog.blog.service.impl;

import com.blog.blog.entities.Comment;
import com.blog.blog.entities.Post;
import com.blog.blog.exceptions.ResourceNotFoundException;
import com.blog.blog.payloads.CommentDto;
import com.blog.blog.repositorie.CommentRepo;
import com.blog.blog.repositorie.PostRepo;
import com.blog.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post); // Set the post association

        Comment savedComment = this.commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void delete(Long id) {
        commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        this.commentRepo.deleteById(id);
    }
}
