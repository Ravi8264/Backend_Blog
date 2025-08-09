package com.blog.blog.service.impl;

import com.blog.blog.entities.Comment;
import com.blog.blog.entities.Post;
import com.blog.blog.entities.User;
import com.blog.blog.exceptions.ResourceNotFoundException;
import com.blog.blog.payloads.CommentDto;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.repositorie.CommentRepo;
import com.blog.blog.repositorie.PostRepo;
import com.blog.blog.repositorie.UserRepo;
import com.blog.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = this.commentRepo.save(comment);

        // Manually create CommentDto to avoid mapping conflicts
        CommentDto savedCommentDto = new CommentDto();
        savedCommentDto.setId(savedComment.getId());
        savedCommentDto.setContent(savedComment.getContent());
        savedCommentDto.setUserId(savedComment.getUser().getId());
        savedCommentDto.setUser(modelMapper.map(savedComment.getUser(), UserDto.class));

        return savedCommentDto;
    }

    @Override
    public void delete(Long id) {
        commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        this.commentRepo.deleteById(id);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        List<Comment> comments = commentRepo.findByPost(post);
        return comments.stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(comment.getId());
                    commentDto.setContent(comment.getContent());
                    commentDto.setUserId(comment.getUser().getId());
                    commentDto.setUser(modelMapper.map(comment.getUser(), UserDto.class));
                    return commentDto;
                })
                .collect(Collectors.toList());
    }
}
