package com.myapp.ecommerce.service.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.ecommerce.entity.awsDto.AddToCartMessage;
import com.myapp.ecommerce.entity.awsDto.RefundCartMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SqsProducer {

    private final AmazonSQS amazonSQS;

    @Value("${SQS_ADD_TO_CART_QUEUE_URL}")
    private String addToCartQueueUrl;

    @Value("${SQS_REFUND_CART_QUEUE_URL}")
    private String refundCartQueueUrl;

    public SqsProducer(AmazonSQS amazonSQSClient) {
        this.amazonSQS = amazonSQSClient;
    }

    public void addToCartProducerSQS(String productId, String userId, Integer quantity) {
        AddToCartMessage message = new AddToCartMessage(productId, userId,quantity);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageBody = objectMapper.writeValueAsString(message);
            amazonSQS.sendMessage(addToCartQueueUrl, messageBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void refundFromCartProducerSQS(String productId, String userId, Integer quantity){
        RefundCartMessage message = new RefundCartMessage(productId,quantity,userId);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageBody = objectMapper.writeValueAsString(message);
            log.info("VALOR DA MENSAGEM: "  + message);
            log.info("BODY DA MENSAGEM: "  + messageBody);
            amazonSQS.sendMessage(refundCartQueueUrl, messageBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
