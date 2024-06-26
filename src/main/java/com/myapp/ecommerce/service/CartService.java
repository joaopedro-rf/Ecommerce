package com.myapp.ecommerce.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.exception.InvalidRequestException;
import com.myapp.ecommerce.repository.CartRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    public Cart saveCart(Cart cart) {
        return cartRepository.saveAndReturn(cart);
    }

    public Cart createCart(String userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setPrice(0);
        cart.setProductList(new ArrayList<>());
        return cart;
    }

    public void addProductToCart(Cart cart, String productId, Integer quantity) {
        List<Product> productList = cart.getProductList();
        Product product = productService.findProductById(productId);

        boolean productAlreadyInCart = false;
        for (Product products : productList) {
            if (products.getProductId().equals(productId)) {
                products.setQuantity(products.getQuantity() + quantity);
                productAlreadyInCart = true;
                log.info("Irá executar quando productAlreadyInCart = true :" + productAlreadyInCart);
                log.info("products.getId : " + products.getProductId());
                break;
            }
        }

        if (!productAlreadyInCart) {
            Product newProduct = new Product();
            newProduct.setProductId(productId);
            newProduct.setQuantity(quantity);
            newProduct.setDescription(product.getDescription());
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            newProduct.setImageUrl(product.getImageUrl());
            productList.add(newProduct);
        }
        calculateTotalPrice(cart);
        cart.setProductList(productList);
        this.saveCart(cart);
    }

    public void deleteProductFromCart(Cart cart, String productId, Integer quantity) throws InvalidRequestException {
        List<Product> productList = cart.getProductList();
        Product product = productService.findProductById(productId);

        if (product == null) {
            throw new NotFoundException("Product not found with ID: " + productId);
        }

        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product p = iterator.next();
            if (p.getProductId().equals(productId)) {
                if (p.getQuantity() < quantity) {
                    throw new InvalidRequestException("Not enough quantity of product with ID: " + productId);
                }
                p.setQuantity(p.getQuantity() - quantity);
                break;
            }
        }
        calculateTotalPrice(cart);
        cart.setProductList(productList);
        this.saveCart(cart);

    }


    public Cart findCartById(String cartId) {
        return cartRepository.findCartById(cartId);
    }

    public ResponseEntity<Cart> findCartByUserId(String userId) {
        log.info("User id :" + userId);
        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteCartById(String cartId) {
        cartRepository.deleteCartById(cartId);
    }

    private void calculateTotalPrice(Cart cart) {
        double totalPrice = 0;
        for (Product product : cart.getProductList()) {
            totalPrice += product.getPrice() * product.getQuantity();

        }
        cart.setPrice(totalPrice);
    }
}
