package com.blog.blog.controller;

import com.blog.blog.payloads.AppConstant;
import com.blog.blog.payloads.PostDto;
import com.blog.blog.payloads.PostResponse;
import com.blog.blog.service.FileService;
import com.blog.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${file.upload.path}")
    private String uploadPath;

    // 1. Create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            @PathVariable Long userId,
            @PathVariable Long categoryId) {

        PostDto createdPost = postService.create(postDto, categoryId, userId);
        return ResponseEntity.ok(createdPost);
    }

    // 2. Update Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @Valid @RequestBody PostDto postDto,
            @PathVariable Long postId) {
        PostDto updatedPost = postService.update(postDto, postId);
        return ResponseEntity.ok(updatedPost);
    }

    // 3. Delete Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    // 4. Get All Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // 5. Get Post by ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto post = postService.getById(postId);
        return ResponseEntity.ok(post);
    }

    // 6. Get Posts by Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    // 7. Get Posts by User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
        List<PostDto> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    // 8. Search Posts
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keyword) {
        List<PostDto> posts = postService.searchPosts(keyword);
        return ResponseEntity.ok(posts);
    }

    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long postId) throws IOException {

        String fileName = fileService.uploadImage(uploadPath, image);

        // Update post with image name
        PostDto postDto = postService.getById(postId);
        postDto.setImageName(fileName);
        PostDto updatedPost = postService.update(postDto, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }
}
