package com.myapp.ecommerce.service.aws;

import com.amazonaws.services.s3.model.ObjectLockMode;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.ecommerce.entity.AddToCartMessage;
import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.service.CartService;
import jakarta.persistence.LockModeType;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Log4j2
public class SqsConsumer {

    private final AmazonSQS amazonSQSClient;
    private final CartService cartService;

    private final Lock lock = new ReentrantLock();

    @Autowired
    public SqsConsumer(AmazonSQS amazonSQSClient, CartService cartService) {
        this.amazonSQSClient = amazonSQSClient;
        this.cartService = cartService;
    }


    @Scheduled(fixedDelay = 100)
    public void consumeMessageSQS() {
        if (lock.tryLock()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ReceiveMessageResult result = amazonSQSClient.receiveMessage("https://sqs.us-east-1.amazonaws.com/079393629981/addToCart");

                if (!result.getMessages().isEmpty()) {
                    Message message = result.getMessages().get(0);

                    AddToCartMessage addToCartMessage = objectMapper.readValue(message.getBody(), AddToCartMessage.class);

                    String userId = addToCartMessage.getUserId();
                    String productId = addToCartMessage.getProductId();
                    Integer quantity = addToCartMessage.getQuantity();


                    Cart cart = cartService.findCartByUserId(userId);
                    log.info("OBJETO CART ++++++  " + cart);
                    if (cart == null) {
                        cart = cartService.createCart(userId);
                    }
                    cartService.addProductToCart(cart, productId, quantity);

                    amazonSQSClient.deleteMessage(new DeleteMessageRequest()
                            .withQueueUrl("https://sqs.us-east-1.amazonaws.com/079393629981/addToCart")
                            .withReceiptHandle(message.getReceiptHandle()));
                }


            } catch (QueueDoesNotExistException e) {
                System.out.println("Fila nao existe" + e.getMessage());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {

            log.info("Uma mensagem já está sendo processada. Aguarde.");
        }


    }


}
