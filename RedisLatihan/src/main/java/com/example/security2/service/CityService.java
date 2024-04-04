package com.example.security2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CityService {
    private final RestClient restClient;

    public  CityService(){
        restClient = RestClient.builder()
                .baseUrl("https://api.rajaongkir.com/starter/city")
                .defaultHeader("key","185b008ed7919f09126b08ad56d298ef" )
                .build();
    }

    public String getAllDataDariApi(){
        return  restClient.get()
                .retrieve()
                .body(String.class);
    }

    public String getDataDariApibyId(int id){
        String url = "https://api.rajaongkir.com/starter/city?id=" + id;
        return  restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
    }
}
