package com.blog.blog.controller;

import com.blog.blog.payloads.ApiResponce;
import com.blog.blog.payloads.CommentDto;
import com.blog.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")

public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId) {
        CommentDto commentDto1 = commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponce> delete(@PathVariable Long id) {
        commentService.delete(id);
        return new ResponseEntity<ApiResponce>(new ApiResponce("Comment deleted Successfully", true), HttpStatus.OK);
    }
}
