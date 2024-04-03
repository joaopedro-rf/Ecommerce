package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.myapp.ecommerce.entity.Address;
import com.myapp.ecommerce.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AddressRepository extends DynamoDBMapper {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public AddressRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }

    public Address saveAndReturn(Address address){
        dynamoDBMapper.save(address);
        return address;
    }

    public Address findAddressById(String id){
        return dynamoDBMapper.load(Address.class, id);
    }

    public void deleteAddressById(String id){
        dynamoDBMapper.delete(this.findAddressById(id));
    }
}
