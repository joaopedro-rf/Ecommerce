package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.User;
import com.myapp.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User SaveUser(User user){
        return userRepository.saveAndReturn(user);
    }
}
