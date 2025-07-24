package com.blog.blog.controller;

import com.blog.blog.payloads.ApiResponce;
import com.blog.blog.payloads.CategoryDto;
import com.blog.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create new category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.create(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Update category by id
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long id) {
        CategoryDto updatedCategory = categoryService.update(categoryDto, id);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete category by id
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponce> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(new ApiResponce("Category deleted successfully", true));
    }

    // Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.get(id);
        return ResponseEntity.ok(categoryDto);
    }

    // Get all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> categories = categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
