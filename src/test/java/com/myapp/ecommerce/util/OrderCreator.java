package com.myapp.ecommerce.util;

import com.myapp.ecommerce.entity.Order;

import java.util.Map;

public class OrderCreator {

    public static Order createOrderToBeSaved(){
        return new Order("000","012",false, "Shipped", Map.of("product1", 1, "product2", 2),123.23 );
    }

    public static Order createValidOrder(){
        return new Order("222","333", true, "Delivered", Map.of("product1", 5, "product2", 5), 555.12);
    }
}
