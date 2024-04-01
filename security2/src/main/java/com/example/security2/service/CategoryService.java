package com.example.security2.service;


import com.example.security2.dto.CategoryDto;
import com.example.security2.entity.Category;
import com.example.security2.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDto convertToDTO (Category category){
        return new CategoryDto(category.getCategoryId(), category.getCategoryName());
    }
}