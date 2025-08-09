package com.blog.blog.repositorie;

import com.blog.blog.entities.Comment;
import com.blog.blog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
