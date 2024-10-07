package com.taskone.delivery.server;
import java.security.MessageDigest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional
    @GetMapping("/users/all")
    public List<User> getAllUsers(){ return userService.getAllUsers(); }

    @PostMapping("/users/new")
    public User createUser(@RequestBody User user) { return userService.saveUser(user);}

}
