package com.blog.blog.repositorie;


import com.blog.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository< Category,Long> {
}
