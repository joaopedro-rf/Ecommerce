package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createCart(@RequestBody Cart cart){
        return cartService.SaveCart(cart);
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
