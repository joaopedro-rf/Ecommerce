package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product SaveProduct(Product product){
        return productRepository.saveAndReturn(product);
    }

    public Product findProductById(String productId){
        return productRepository.load(Product.class, productId);
    }

    public List<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }
    public void deleteProductById(String productId){
        productRepository.delete(this.findProductById(productId));
    }
}
