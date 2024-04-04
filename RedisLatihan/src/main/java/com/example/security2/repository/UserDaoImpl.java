//package com.example.security2.repository;
//
//import com.example.security2.entity.UserRedis;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class UserDaoImpl implements UserDao{
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    private static final String Key = "USER";
//
//    @Override
//    public boolean saveUser(UserRedis userRedis) {
//        try{
//            redisTemplate.opsForHash().put(Key, userRedis.getId(), userRedis);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public List<UserRedis> fetchAllUser() {
//        List<UserRedis> users;
//        users = redisTemplate.opsForHash().values(Key);
//        return users;
//    }
//}
