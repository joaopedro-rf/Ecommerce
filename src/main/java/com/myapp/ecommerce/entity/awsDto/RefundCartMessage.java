package com.myapp.ecommerce.entity.awsDto;

import java.util.Objects;

public class RefundCartMessage {

    private String productId;
    private String userId;
    private Integer quantity;

    public RefundCartMessage() {
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "RefundCartMessage{" +
                "productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefundCartMessage that = (RefundCartMessage) o;
        return Objects.equals(productId, that.productId) && Objects.equals(userId, that.userId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId, quantity);
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public RefundCartMessage(String productId, Integer quantity, String userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
