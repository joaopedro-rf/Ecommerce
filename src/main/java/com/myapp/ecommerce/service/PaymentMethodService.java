package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.PaymentMethod;
import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod SavePayment(PaymentMethod paymentMethod){
        return paymentMethodRepository.saveAndReturn(paymentMethod);
    }

    public PaymentMethod findPaymentById(String PaymentId){
        return paymentMethodRepository.load(PaymentMethod.class, PaymentId);
    }
    public void deletePaymentById(String PaymentId){
        paymentMethodRepository.delete(this.findPaymentById(PaymentId));
    }
}
