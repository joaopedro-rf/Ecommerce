package com.myapp.ecommerce.util;

import com.myapp.ecommerce.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class UserCreator {

    public static User createUserToBeSaved() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = dateFormat.parse("2024-03-25T19:15:40.779+00:00");
        return new User("123", date,null,"Joao", "987654321" ,"usuario@example.com","senha123");
    }

    public static User createValidUser() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = dateFormat.parse("2024-03-25T19:15:40.779+00:00");
        return new User("123", date,null,"Pedro", "987654321" ,"usuario@example.com","senha123");
    }

    public static User createValidUpdatedUser() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = dateFormat.parse("2024-03-25T19:15:40.779+00:00");
        return new User("123", date,null,"Ribeiro", "987654321" ,"usuario@example.com","senha123");
    }
}
