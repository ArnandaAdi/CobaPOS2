package com.example.security2.controller;

import com.example.security2.dto.CostDTO;
import com.example.security2.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data")
public class CostController {

    @Autowired
    private CostService costService;

    @PostMapping("/cost")
    public CostDTO getDataAPI (){
        return  costService.getDataDariAPI();
    }



}

