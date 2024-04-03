package com.myapp.ecommerce.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class AddToCartMessage {
    private String productId;
    private String userId;

    @Override
    public String toString() {
        return "AddToCartMessage{" +
                "productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddToCartMessage message = (AddToCartMessage) o;
        return Objects.equals(productId, message.productId) && Objects.equals(userId, message.userId) && Objects.equals(quantity, message.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId, quantity);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private Integer quantity;

    public AddToCartMessage(String productId, String userId, Integer quantity) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public AddToCartMessage() {
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
