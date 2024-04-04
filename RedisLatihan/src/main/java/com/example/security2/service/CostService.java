package com.example.security2.service;

import com.example.security2.dto.CostDTO;
import com.example.security2.utils.MappingUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CostService {

    private final WebClient webClient;

    public CostService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.rajaongkir.com/starter")
                .defaultHeader("key", "185b008ed7919f09126b08ad56d298ef")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .build();
    }

    public CostDTO getDataDariAPI() {

        String request = "origin=501&destination=114&weight=1700&courier=jne";

        String jsonResponse = webClient.post()
                .uri("/cost")
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            ObjectMapper objectMapper = MappingUtil.getObjectMapper();
            CostDTO costDTO = objectMapper.readValue(jsonResponse, CostDTO.class);

            return costDTO;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;

        }
    }

}