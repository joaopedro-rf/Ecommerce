package com.myapp.ecommerce.service.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.InvalidMessageContentsException;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SqsService {

    @Autowired
    private AmazonSQS amazonSQS;

    public void publishMessage(String message){
        String addToCart = "https://sqs.us-east-1.amazonaws.com/079393629981/addToCart";
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(addToCart)
                .withMessageBody(message);
        amazonSQS.sendMessage(sendMessageRequest);
    }
}
