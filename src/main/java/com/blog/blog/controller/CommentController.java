package com.blog.blog.controller;

import com.blog.blog.payloads.ApiResponce;
import com.blog.blog.payloads.CommentDto;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")

public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        CommentDto commentDto1 = commentService.createComment(commentDto, postId, commentDto.getUserId());
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<List<CommentDto>>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponce> delete(@PathVariable Long id) {
        System.out.println("CommentController: Received DELETE request for comment ID: " + id);
        commentService.delete(id);
        System.out.println("CommentController: Comment deletion completed successfully");
        return new ResponseEntity<ApiResponce>(new ApiResponce("Comment deleted Successfully", true), HttpStatus.OK);
    }
}
