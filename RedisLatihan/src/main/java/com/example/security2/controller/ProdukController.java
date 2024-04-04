package com.example.security2.controller;

import com.example.security2.entity.Produk;
import com.example.security2.repository.ProdukDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ProdukController {

    @Autowired
    private ProdukDao dao;

    @PostMapping("/save")
    public Produk save(@RequestBody Produk produk){
        return dao.save(produk);
    }

    @GetMapping
    public List<Produk> getAllProduk(){
        return dao.findAll();
    }

    @GetMapping("/{id}")
    public Produk findProduk(@PathVariable int id){
        return dao.findProdukById(id);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
        return dao.deleteProduk(id);
    }

}
