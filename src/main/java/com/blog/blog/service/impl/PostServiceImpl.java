package com.blog.blog.service.impl;

import com.blog.blog.entities.Category;
import com.blog.blog.entities.Post;
import com.blog.blog.entities.User;
import com.blog.blog.exceptions.ResourceNotFoundException;
import com.blog.blog.payloads.PostDto;
import com.blog.blog.payloads.PostResponse;
import com.blog.blog.repositorie.CategoryRepo;
import com.blog.blog.repositorie.PostRepo;
import com.blog.blog.repositorie.UserRepo;
import com.blog.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
        @Autowired
        private PostRepo postRepo;

        @Autowired
        private ModelMapper modelMapper;
        @Autowired
        private UserRepo userRepo;
        @Autowired
        private CategoryRepo categoryRepo;

        // ✅ Create Post
        @Override
        public PostDto create(PostDto postDto, Long categoryId, Long userId) {
                Post post = modelMapper.map(postDto, Post.class);

                User user = userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

                Category category = categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

                post.setImageName("default.img");
                post.setCreatedDate(new Date());
                post.setUser(user);
                post.setCategory(category);
                Post savedPost = postRepo.save(post);
                return modelMapper.map(savedPost, PostDto.class);
        }

        // ✅ Update Post
        @Override
        public PostDto update(PostDto postDto, Long id) {
                Post post = postRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                post.setImageName(postDto.getImageName());

                Post updatedPost = postRepo.save(post);
                return modelMapper.map(updatedPost, PostDto.class);
        }

        // ✅ Delete Post
        @Override
        public void delete(Long id) {
                Post post = postRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
                postRepo.delete(post);
        }

        // ✅ Get All Posts
        @Override
        public PostResponse getAllPosts(int pagenumber, int pagesize, String sortBy, String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();
                Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
                Page<Post> pagePosts = postRepo.findAll(pageable);
                List<Post> allpost = pagePosts.getContent();
                List<PostDto> postDtos = allpost.stream()
                                .map(post -> modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
                PostResponse postResponse = new PostResponse();
                postResponse.setContent(postDtos);
                postResponse.setPageNumber(pagePosts.getNumber());
                postResponse.setPageSize(pagePosts.getSize());
                postResponse.setTotalElement(pagePosts.getTotalElements());
                postResponse.setTotalPages(pagePosts.getTotalPages());
                postResponse.setLastPage(pagePosts.isLast());
                return postResponse;
        }

        // ✅ Get Post by ID
        @Override
        public PostDto getById(Long id) {
                Post post = postRepo.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
                return modelMapper.map(post, PostDto.class);
        }

        // ✅ Get Posts by Category
        @Override
        public List<PostDto> getPostsByCategory(Long categoryId) {
                Category category = categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

                List<Post> posts = postRepo.findByCategory(category);
                return posts.stream()
                                .map(post -> modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
        }

        // ✅ Get Posts by User
        @Override
        public List<PostDto> getPostsByUserId(Long userId) {
                User user = userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

                List<Post> posts = postRepo.findByUser(user);
                return posts.stream()
                                .map(post -> modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
        }

        // ✅ Search Posts by Title Keyword
        @Override
        public List<PostDto> searchPosts(String keyword) {
                List<Post> posts = postRepo.findByTitleContaining(keyword);
                return posts.stream()
                                .map(post -> modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
        }
}
