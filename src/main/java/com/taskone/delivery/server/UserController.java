package com.taskone.delivery.server;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.bucket4j.Bucket;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    Bucket bucket;

    @Transactional
    @GetMapping("/users/all")
    public List<User> getAllUsers(){ return userService.getAllUsers(); }

    @PostMapping("/users/new")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(bucket.tryConsume(1)){
            User newUser = userService.saveUser(user);
            return ResponseEntity.ok(newUser);
        } else {
            return ResponseEntity.status(429).build();
        }
    }

    @DeleteMapping("/users/user/delete")
    public void deleteUser(@RequestBody DeleteUserRequest deleteUser){
        Long userId = deleteUser.getUserId();
        userService.deleteUser(userId);
    }


}
