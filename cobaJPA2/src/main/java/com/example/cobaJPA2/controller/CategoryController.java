package com.example.cobaJPA2.controller;

import com.example.cobaJPA2.entity.Category;
import com.example.cobaJPA2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Optional<Category> getCategoryById(@PathVariable("id") Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/input")
    public Category addNewCategory(@RequestBody Category category) {
        return categoryService.addNewCategory(category);
    }
}

