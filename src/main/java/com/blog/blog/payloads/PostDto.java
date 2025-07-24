package com.blog.blog.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class PostDto {
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    @NotBlank(message = "Content is required")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    private String content;
    private String imageName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
    private Date createdDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();
}
