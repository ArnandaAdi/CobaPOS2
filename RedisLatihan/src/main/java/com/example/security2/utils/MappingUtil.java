package com.example.security2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class MappingUtil {
    @Getter
    private  static  final ObjectMapper objectMapper = new ObjectMapper();
}