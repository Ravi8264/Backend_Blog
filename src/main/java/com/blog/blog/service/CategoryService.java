package com.blog.blog.service;

import com.blog.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);
    CategoryDto update(CategoryDto categoryDto, Long id);
    void delete(Long id);
    CategoryDto get(Long id);
    List<CategoryDto> getCategories();
}