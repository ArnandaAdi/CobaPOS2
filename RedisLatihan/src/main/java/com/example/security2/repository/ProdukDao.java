package com.example.security2.repository;

import com.example.security2.entity.Produk;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdukDao {

    public static final String HASH_KEY = "Produk";
    private RedisTemplate template;

    public Produk save(Produk produk){
        template.opsForHash().put(HASH_KEY,produk.getId(),produk);
        return produk;
    }

    public List<Produk> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public Produk findProdukById(int id){
        return (Produk) template.opsForHash().get(HASH_KEY,id);
    }

    public String deleteProduk(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "Produk remove";

    }
}
