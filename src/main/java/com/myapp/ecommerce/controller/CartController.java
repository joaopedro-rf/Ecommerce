package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.entity.awsDto.AddToCartMessage;
import com.myapp.ecommerce.entity.awsDto.RefundCartMessage;
import com.myapp.ecommerce.service.aws.SqsProducer;
import com.myapp.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carts")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private SqsProducer sqsProducer;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToCart(@RequestBody AddToCartMessage request){
       sqsProducer.addToCartProducerSQS(request.getProductId(), request.getUserId(), request.getQuantity());
    }

    @PostMapping("/refundProduct")
    @ResponseStatus(HttpStatus.OK)
    public void refundProductFromCart(@RequestBody List<RefundCartMessage> refundItemsList){
        for (RefundCartMessage item : refundItemsList) {
            sqsProducer.refundFromCartProducerSQS(item.getProductId(), item.getUserId(),item.getQuantity());
        }
    }

    @GetMapping("/findByCart/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public Cart findCartByCartId(@PathVariable String cartId){
        return cartService.findCartById(cartId);
    }

    @GetMapping("/findByUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cart> findCartByUserId(@PathVariable String userId){
        return cartService.findCartByUserId(userId);
    }

    @DeleteMapping("/delete/{cartId}")
    public void deleteCartById(@PathVariable String cartId){
        cartService.deleteCartById(cartId);
    }
}
