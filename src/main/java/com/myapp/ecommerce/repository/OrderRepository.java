package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.myapp.ecommerce.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository extends DynamoDBMapper {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;


    public OrderRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }

    public Order saveAndReturn(Order order){
        dynamoDBMapper.save(order);
        return order;
    }

    public Order findOrderById(String id){
        return dynamoDBMapper.load(Order.class, id);
    }

    public void deleteOrderById(String id){
        dynamoDBMapper.delete(this.findOrderById(id));
    }
}
