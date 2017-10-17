package com.example.app.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    public User save(User user){
        String email = user.getEmail();
        user.setEmail(email);
        return userMap.put(email,user);
    }

    public User save(String email, User user){
        return userMap.put(email,user);
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public void delete(String email){
        userMap.remove(email);
    }

    public boolean exists(String email){
        return userMap.containsKey(email);
    }
}