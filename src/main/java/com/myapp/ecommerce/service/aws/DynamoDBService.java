package com.myapp.ecommerce.service.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamoDBService {

    @Autowired
    public DynamoDBService(AmazonDynamoDB amazonDynamoDB) {
    }
}