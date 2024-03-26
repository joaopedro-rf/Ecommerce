package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.PaymentMethod;
import com.myapp.ecommerce.entity.User;
import com.myapp.ecommerce.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payments")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethod createPayment(@RequestBody PaymentMethod paymentMethod){
        return paymentMethodService.SavePayment(paymentMethod);
    }

    @GetMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentMethod findUserById(@PathVariable String paymentId){
        return paymentMethodService.findPaymentById(paymentId);
    }

    @DeleteMapping("/delete/{paymentId}")
    public void deletePaymentById(@PathVariable String paymentId){
        paymentMethodService.deletePaymentById(paymentId);
    }
}
