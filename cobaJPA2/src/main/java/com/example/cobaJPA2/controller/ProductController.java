package com.example.cobaJPA2.controller;

import com.example.cobaJPA2.entity.Product;
import com.example.cobaJPA2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/input")
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }
}
