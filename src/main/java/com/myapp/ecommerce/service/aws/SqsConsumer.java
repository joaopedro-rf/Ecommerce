package com.myapp.ecommerce.service.aws;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.ecommerce.controller.MessageController;
import com.myapp.ecommerce.entity.awsDto.AddToCartMessage;
import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.entity.awsDto.RefundCartMessage;
import com.myapp.ecommerce.exception.InvalidRequestException;
import com.myapp.ecommerce.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Log4j2
public class SqsConsumer {

    @Autowired
    private MessageController messageController;

    private final AmazonSQS amazonSQSClient;
    private final CartService cartService;

    @Value("${SQS_ADD_TO_CART_QUEUE_URL}")
    private String addToCartQueueUrl;

    @Value("${SQS_REFUND_CART_QUEUE_URL}")
    private String refundCartQueueUrl;

    private final Lock lock = new ReentrantLock();

    @Autowired
    public SqsConsumer(AmazonSQS amazonSQSClient, CartService cartService) {
        this.amazonSQSClient = amazonSQSClient;
        this.cartService = cartService;
    }

    @Scheduled(fixedDelay = 1000)
    public void consumeAddToCartMessageSQS() {
        if (lock.tryLock()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                        .withQueueUrl(addToCartQueueUrl)
                        .withWaitTimeSeconds(20);
                ReceiveMessageResult result = amazonSQSClient.receiveMessage(receiveMessageRequest);

                if (!result.getMessages().isEmpty()) {
                    Message message = result.getMessages().get(0);

                    AddToCartMessage addToCartMessage = objectMapper.readValue(message.getBody(), AddToCartMessage.class);

                    String userId = addToCartMessage.getUserId();
                    String productId = addToCartMessage.getProductId();
                    Integer quantity = addToCartMessage.getQuantity();

                    Cart cart = cartService.findCartByUserId(userId).getBody();
                    if (cart == null) {
                        cart = cartService.createCart(userId);
                    }
                    log.info("VALOR DE CART: " + cart + " PRODUCT: " + productId + " QUANTITY:  " + quantity);
                    cartService.addProductToCart(cart, productId, quantity);

                    DeleteMessageRequest deleteRequest = new DeleteMessageRequest()
                            .withQueueUrl(addToCartQueueUrl)
                            .withReceiptHandle(message.getReceiptHandle());
                    amazonSQSClient.deleteMessage(deleteRequest);
                    messageController.sendMessageToWebSocket(
                            String.format("Add to cart message processed: %s", addToCartMessage));
                }
                else {
                    System.out.println("Empty queue");
                }
            } catch (QueueDoesNotExistException e) {
                System.out.println("Queue doesnt exist" + e.getMessage());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (AmazonSQSException e) {
                System.out.println("Error deleting message from SQS queue: " + e.getErrorMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("A message is already being processed.");
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void refundCartMessageSQS() {
        if (lock.tryLock()) {
            Message message = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                        .withQueueUrl(refundCartQueueUrl)
                        .withWaitTimeSeconds(20);
                ReceiveMessageResult result = amazonSQSClient.receiveMessage(receiveMessageRequest);

                if (!result.getMessages().isEmpty()) {
                    message = result.getMessages().get(0);

                    RefundCartMessage refundCartMessage = objectMapper.readValue(message.getBody(), RefundCartMessage.class);

                    String userId = refundCartMessage.getUserId();
                    String productId = refundCartMessage.getProductId();
                    Integer quantity = refundCartMessage.getQuantity();

                    Cart cart = cartService.findCartByUserId(userId).getBody();
                    if (cart == null) {
                        return;
                    }


                    cartService.deleteProductFromCart(cart, productId, quantity);
                    DeleteMessageRequest deleteRequest = new DeleteMessageRequest()
                            .withQueueUrl(refundCartQueueUrl)
                            .withReceiptHandle(message.getReceiptHandle());
                    amazonSQSClient.deleteMessage(deleteRequest);
                    messageController.sendMessageToWebSocket(
                            String.format("Add to cart message processed: %s", refundCartMessage));
                }
            } catch (QueueDoesNotExistException e) {
                System.out.println("Queue doesnt exist" + e.getMessage());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (AmazonSQSException e) {
                System.out.println("Error deleting message from SQS queue: " + e.getErrorMessage());
            } catch (InvalidRequestException e) {
                DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest()
                        .withQueueUrl(refundCartQueueUrl)
                        .withReceiptHandle(message.getReceiptHandle());

                amazonSQSClient.deleteMessage(deleteMessageRequest);

                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("A message is already being processed.");
        }
    }



}

