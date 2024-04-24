package com.myapp.ecommerce.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product SaveProduct(Product product){
        return productRepository.saveAndReturn(product);
    }

    public Product findProductById(String productId){
        return productRepository.findProductById( productId);
    }

    public Page<Product> getAllPageableProducts(int pageNumber, int pageSize) {
        return productRepository.findAllProducts(pageNumber, pageSize);
    }

    public List<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }
    public void deleteProductById(String productId){
        productRepository.deleteProductById(productId);
    }
}
