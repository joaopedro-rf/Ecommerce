package com.myapp.ecommerce.util;

import com.myapp.ecommerce.entity.Product;

public class ProductCreator {

    public static Product createProductToBeSaved(){
        return new Product("123","Smartphone", "Biurifou cellphone",111 , 20.99,"http://www.biurifouSmartphone.com");
    }

    public static Product createValidProduct(){
        return new Product("321", "Compiuter", "Amaizing compiuter" , 123,21.99, "http://www.biurifouCompiuter.com");
    }

    public static Product createValidUpdatedProduct(){
        return new Product("456", "Cerula", "Incredibou cerula" , 222,22.99, "http://www.biurifouCerula.com");
    }
}
