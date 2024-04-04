//package com.example.security2.service;
//
//import com.example.security2.entity.UserRedis;
//import com.example.security2.repository.UserDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserRedisServiceImpl implements UserRedisService{
//
//    @Autowired
//    private UserDao userDao;
//    @Override
//    public boolean saveUser(UserRedis userRedis) {
//        return userDao.saveUser(userRedis);
//    }
//
//    @Override
//    public List<UserRedis> fetchAllUser() {
//        return userDao.fetchAllUser();
//    }
//}
