package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Order;
import com.myapp.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order SaveOrder(Order order){
        return orderRepository.saveAndReturn(order);
    }

    public Order findOrderById(String orderId){
        return orderRepository.load(Order.class, orderId);
    }
    public void deleteOrderById(String orderId){
        orderRepository.delete(this.findOrderById(orderId));
    }
}
