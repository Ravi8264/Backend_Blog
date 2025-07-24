package com.blog.blog.service.impl;

import com.blog.blog.entities.Category;
import com.blog.blog.exceptions.ResourceNotFoundException;
import com.blog.blog.payloads.CategoryDto;
import com.blog.blog.repositorie.CategoryRepo;
import com.blog.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        categoryRepo.delete(category);
    }

    @Override
    public CategoryDto get(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categoryList = categoryRepo.findAll();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }
}
