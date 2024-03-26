package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.User;
import com.myapp.ecommerce.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User SaveUser(User user){
        return userRepository.saveAndReturn(user);
    }

    public User findUserById(String id){
        return userRepository.findUserById(id);
    }

    public void deleteUserById(String id){
        userRepository.delete(this.findUserById(id));
    }

    public ResponseEntity<User> updateUser(User updatedUser, String id){
        updatedUser = userRepository.saveAndReturnUpdatedUser(updatedUser,id);

        if(updatedUser == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return ResponseEntity.ok(updatedUser);
    }


}
