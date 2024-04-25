package com.myapp.ecommerce.util;

import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartCreator {

    public static Cart createCartToBeSaved( ){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("1", "Product 1", "Description 1", 10, 20.99,"www.foto.com"));
        productList.add(new Product("2", "Product 2", "Description 2", 20, 21.99, "www.foto.com"));
        return new Cart("898","989",999.99, productList);
    }

    public static Cart createValidCart(){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("3", "Product 3", "Description 3", 10, 20.99, "www.foto.com"));
        productList.add(new Product("4", "Product 4", "Description 4", 20, 21.99, "www.foto.com"));
        return new Cart("777","555", 888.88, productList);
    }
}
