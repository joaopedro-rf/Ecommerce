package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.entity.Order;
import com.myapp.ecommerce.entity.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class CartRepository extends DynamoDBMapper {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public CartRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }

    public Cart saveAndReturn(Cart cart) {
        dynamoDBMapper.save(cart);
        return cart;
    }

    public Cart findCartById(String cartId) {
        return dynamoDBMapper.load(Cart.class, cartId);
    }

    public Cart findCartByUserId(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        PaginatedScanList<Cart> carts = dynamoDBMapper.scan(Cart.class, scanExpression);
        log.info("VALOR DO CARRINHO NO REPOSITORY: " + carts);

        return carts.stream()
                .filter(cart -> cart.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

    }


    public void deleteCartById(String id) {
        dynamoDBMapper.delete(this.findCartById(id));
    }


}

