package com.example.cobaJPA2.dto;

import com.example.cobaJPA2.entity.Product;

public class ProductRequest {
    private String productName;
    private int productStock;
    private int productPrice;

    public ProductRequest(String productName, int productStock, int productPrice) {
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductStock() {
        return productStock;

    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductPrice() {
        return productPrice;

    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public Product toEntity(){
        Product product = new Product();
        product.setProductName(productName);
        product.setProductStock(productStock);
        product.setProductPrice(productPrice);
        return product;

    }
}
