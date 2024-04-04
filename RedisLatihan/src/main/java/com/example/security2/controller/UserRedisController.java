//package com.example.security2.controller;
//
//import com.example.security2.entity.UserRedis;
//import com.example.security2.service.UserRedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/userredis")
//public class UserRedisController {
//
//    @Autowired
//    private UserRedisService userRedisService;
//
//    @PostMapping("/save")
//    public ResponseEntity<String> saveUser(@RequestBody UserRedis userRedis) {
//        boolean result = userRedisService.saveUser(userRedis);
//        if (result)
//            return ResponseEntity.ok("User Created Succesfully");
//        else
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<UserRedis>> fetchAllUserRedis(){
//        List<UserRedis> users;
//        users = userRedisService.fetchAllUser();
//        return ResponseEntity.ok(users);
//    }
//}
