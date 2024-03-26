package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.myapp.ecommerce.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository extends DynamoDBMapper {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    public CartRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }

    public Cart saveAndReturn(Cart cart){
        dynamoDBMapper.save(cart);
        return cart;
    }
}
