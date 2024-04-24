package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.User;
import com.myapp.ecommerce.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
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

    public ResponseEntity<User> register(User user){
        if(userRepository.findUserByEmail(user.getEmail()) !=null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userRepository.saveAndReturn(user), HttpStatus.CREATED);
    }

    public ResponseEntity<User> login(User user){
        User findUser = userRepository.findUserByEmail(user.getEmail());
        if(findUser == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if( user.getPassword().matches(findUser.getPassword())){

            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public User findUserById(String id){
        return userRepository.findUserById(id);
    }

    public void deleteUserById(String id){
        userRepository.deleteUserById(id);
    }

    public ResponseEntity<User> updateUser(User updatedUser, String id){
        updatedUser = userRepository.saveAndReturnUpdatedUser(updatedUser,id);
        
        if(updatedUser == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else return ResponseEntity.ok(updatedUser);
    }


}
