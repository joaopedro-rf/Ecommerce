package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.service.AddToCartMessageService;
import com.myapp.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AddToCartMessageService addToCartMessageService;

    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToCart(@RequestParam String productId, @RequestParam String userId, @RequestParam int quantity){
       addToCartMessageService.addToCartProducerSQS(productId, userId,quantity);
    }

    @GetMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public Cart findCartById(@PathVariable String cartId){
        return cartService.findCartById(cartId);
    }

    @DeleteMapping("/delete/{cartId}")
    public void deleteCartById(@PathVariable String cartId){
        cartService.deleteCartById(cartId);
    }
}
