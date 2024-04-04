package com.example.security2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProvinceService {

    private final RestClient restClient;

    public  ProvinceService(){
        restClient = RestClient.builder()
                .baseUrl("https://api.rajaongkir.com/starter/province")
                .defaultHeader("key","185b008ed7919f09126b08ad56d298ef" )
                .build();
    }

    public String getAllDataDariApi(){
        return  restClient.get()
                .retrieve()
                .body(String.class);
    }

    public String getDataDariApibyId(int id){
        String url = "https://api.rajaongkir.com/starter/province?id=" + id;
        return  restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}