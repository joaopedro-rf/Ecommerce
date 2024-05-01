package com.myapp.ecommerce.controller;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product){
        return productService.SaveProduct(product);
    }

    @GetMapping("/id/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product findProductById(@PathVariable String productId){
        return productService.findProductById(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> findAllPageableProducts(@RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize){
        return productService.getAllPageableProducts(pageNumber, pageSize);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProductByName(@PathVariable String name){
        return productService.findProductByName(name);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProductById(@PathVariable String productId){
        productService.deleteProductById(productId);
    }
}
