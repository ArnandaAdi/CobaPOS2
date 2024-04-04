package com.example.security2.controller;

import com.example.security2.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @Scheduled(cron ="*/15 * * * * *")
    @GetMapping("/province")
    public String getDataAPI(){
        System.out.println("Mengambil data Province");
        return provinceService.getAllDataDariApi();
    }

    @GetMapping("/province/{id}")
    public String getDataAPIbyId(@PathVariable int id){
        return provinceService.getDataDariApibyId(id);
    }

}