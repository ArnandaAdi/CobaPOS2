package com.example.cobaJPA2.dto;


import com.example.cobaJPA2.entity.Category;

public class CategoryRequest {
    private String categoryName;

    public CategoryRequest(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category toEntity(){
        Category category = new Category();
        category.setCategoryName(categoryName);
        return category;
    }

}
