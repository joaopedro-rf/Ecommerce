package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.myapp.ecommerce.entity.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentMethodRepository extends DynamoDBMapper {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public PaymentMethodRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }

    public PaymentMethod saveAndReturn(PaymentMethod paymentMethod){
        dynamoDBMapper.save(paymentMethod);
        return paymentMethod;
    }

}
