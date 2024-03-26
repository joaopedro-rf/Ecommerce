package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.Order;
import com.myapp.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody Order order){
        return orderService.SaveOrder(order);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Order findOrderById(@PathVariable String orderId){
        return orderService.findOrderById(orderId);
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrderById(@PathVariable String orderId){
        orderService.deleteOrderById(orderId);
    }
}
