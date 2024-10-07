package com.taskone.delivery.server;

import com.taskone.delivery.security.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private PasswordHashing passwordHashing;
    public List<User> getAllUsers(){ return userRepository.findAll();}

    //public User saveUser(User user){ return userRepository.save(user);}

    public User saveUser(User user) {
        // Hash the password before saving
        String hashedPassword = passwordHashing.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return user;
    }
}
