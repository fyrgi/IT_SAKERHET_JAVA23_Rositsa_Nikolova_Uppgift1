package com.taskone.delivery.server;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.bucket4j.Bucket;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    Bucket bucket;

    @Transactional
    @GetMapping("/all")
    public List<User> getAllUsers(){ return userService.getAllUsers(); }

    @GetMapping("/user/{id}")
    public ResponseEntity<String> seeUserDetails(@PathVariable Long id) {
        try {
            Optional<User> foundUser = userService.getUserById(id);
            if (foundUser.isPresent()) {
                return new ResponseEntity<>("User found: " + foundUser.get().toString(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/new")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(bucket.tryConsume(1)){
            User newUser = userService.saveUser(user);
            return ResponseEntity.ok(newUser);
        } else {
            return ResponseEntity.status(429).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            if(userService.getUserById(id).isPresent()){
                userService.deleteUser(id);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("User deletion failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
