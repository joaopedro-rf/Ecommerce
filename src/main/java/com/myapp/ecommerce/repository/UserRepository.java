package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
@Log4j2
public class UserRepository extends DynamoDBMapper {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public UserRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }


    public User saveAndReturn(User user) {
        dynamoDBMapper.save(user);
        return user;
    }

    public User saveAndReturnUpdatedUser(User updatedUser, String id) {
        User existingUser = dynamoDBMapper.load(User.class, id);
        long currentTimeMillis = System.currentTimeMillis();

        if (existingUser != null) {
            updateNonNullFields(existingUser, updatedUser);
            existingUser.setUpdateAt(new Date(currentTimeMillis));
            return this.saveAndReturn(existingUser);
        } else return null;
    }

    private void updateNonNullFields(User existingUser, User updatedUser) {
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
    }

    public User findUserById(String id) {
        return this.load(User.class, id);
    }

    public User findUserByEmail(String email) {
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#n", "email");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", new AttributeValue().withS(email));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#n = :val")
                .withExpressionAttributeNames(expressionAttributeNames)
                .withExpressionAttributeValues(expressionAttributeValues);


        PaginatedScanList<User> queryResult = dynamoDBMapper.scan(User.class, scanExpression);


        if (!queryResult.isEmpty()) {
            log.info("RESULTADO DA QUERY" + queryResult.get(0));
            return queryResult.get(0);
        } else {
            return null;
        }
    }

    public void deleteUserById(String id) {
        dynamoDBMapper.delete(this.findUserById(id));
    }

}
