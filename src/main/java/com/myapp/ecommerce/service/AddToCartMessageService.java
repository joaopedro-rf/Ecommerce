package com.myapp.ecommerce.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.ecommerce.entity.AddToCartMessage;
import org.springframework.stereotype.Service;

@Service
public class AddToCartMessageService {

    private final AmazonSQS amazonSQS;


    public AddToCartMessageService(AmazonSQS amazonSQSClient) {
        this.amazonSQS = amazonSQSClient;
    }

    public void addToCartProducerSQS(String productId, String userId, int quantity) {
        AddToCartMessage message = new AddToCartMessage(productId, userId,quantity);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageBody = objectMapper.writeValueAsString(message);
            amazonSQS.sendMessage("https://sqs.us-east-1.amazonaws.com/079393629981/addToCart", messageBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
