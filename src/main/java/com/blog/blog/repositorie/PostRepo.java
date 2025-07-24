package com.blog.blog.repositorie;

import com.blog.blog.entities.Category;
import com.blog.blog.entities.Post;
import com.blog.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    // Get all posts by a user
    List<Post> findByUser(User user);

    // Get all posts by a category
    List<Post> findByCategory(Category category);

    // Search posts by keyword in title
    List<Post> findByTitleContaining(String keyword);
}
