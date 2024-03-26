package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart SaveCart(Cart cart){
        return cartRepository.saveAndReturn(cart);
    }

    public Cart findCartById(String cartId){
        return cartRepository.load(Cart.class, cartId);
    }
    public void deleteCartById(String cartId){
        cartRepository.delete(this.findCartById(cartId));
    }
}
