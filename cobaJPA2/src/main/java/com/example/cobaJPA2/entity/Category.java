package com.example.cobaJPA2.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "category")
public class Category {

    @Column(name = "category_id", nullable = false)
    @Id  @GeneratedValue(strategy = GenerationType.AUTO)

    private Long categoryId;

    @Column(name = "category_name", nullable = false, columnDefinition = "TEXT")
    private String categoryName;

    public Category() {
    }

    public Category(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
