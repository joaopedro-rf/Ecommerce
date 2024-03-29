package com.myapp.ecommerce.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.Objects;

@DynamoDBTable(tableName = "payment_method")
public class PaymentMethod {

    public PaymentMethod() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public PaymentMethod(String paymentMethodId, String userId, boolean isDefault, Date expiryDate, String method) {
        this.paymentMethodId = paymentMethodId;
        this.userId = userId;
        this.isDefault = isDefault;
        this.expiryDate = expiryDate;
        this.method = method;
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String paymentMethodId;
    @DynamoDBAttribute
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return isDefault == that.isDefault && Objects.equals(paymentMethodId, that.paymentMethodId) && Objects.equals(userId, that.userId) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(method, that.method);
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethodId, userId, isDefault, expiryDate, method);
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentMethodId='" + paymentMethodId + '\'' +
                ", userId='" + userId + '\'' +
                ", isDefault=" + isDefault +
                ", expiryDate=" + expiryDate +
                ", method='" + method + '\'' +
                '}';
    }

    @DynamoDBAttribute
    private boolean isDefault;
    @DynamoDBAttribute
    private Date expiryDate;
    @DynamoDBAttribute
    private String method;
}
