package com.example.security2.controller;

import com.example.security2.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/data")
public class CityController {

    @Autowired
    private CityService cityService;

    @Scheduled(cron ="*/10 * * * * *")
    @GetMapping("/city")
    public String getDataAPI(){
        System.out.println("Mengambil data City");
        return cityService.getAllDataDariApi();
    }

    @GetMapping("/city/{id}")
    public String getAPIbyId(@PathVariable int id){
        return cityService.getDataDariApibyId(id);
    }
}
