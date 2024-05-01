package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.User;
import com.myapp.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUserById(@PathVariable String id){
        return userService.findUserById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<User> updateNonNullFiels(@RequestBody User user , @PathVariable String id){
        return userService.updateUser(user,id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable String id){
        userService.deleteUserById(id);
    }
}
