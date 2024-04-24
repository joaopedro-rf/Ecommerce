package com.myapp.ecommerce.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.myapp.ecommerce.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository extends DynamoDBMapper{

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public ProductRepository(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }

    public Product saveAndReturn(Product product) {
        dynamoDBMapper.save(product);
        return product;
    }

    public List<Product> findProductByName(String name) {
        Map<String, String> expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#n", "name");

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", new AttributeValue().withS(name));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#n = :val")
                .withExpressionAttributeNames(expressionAttributeNames)
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(Product.class, scanExpression);
    }

    public Page<Product> findAllProducts(int pageNumber, int pageSize) {
        List<Product> products = dynamoDBMapper.scan(Product.class, new DynamoDBScanExpression());
        int start = pageNumber * pageSize;
        int end = Math.min((start + pageSize), products.size());
        return new PageImpl<>(products.subList(start, end), PageRequest.of(pageNumber, pageSize), products.size());
    }

    public Product findProductById(String id){
        return dynamoDBMapper.load( Product.class ,id);
    }

    public void deleteProductById(String id){
        dynamoDBMapper.delete(this.findProductById(id));
    }
}
