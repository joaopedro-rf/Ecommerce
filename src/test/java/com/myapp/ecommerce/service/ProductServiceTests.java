package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Product;
import com.myapp.ecommerce.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.myapp.ecommerce.util.ProductCreator.createProductToBeSaved;
import static com.myapp.ecommerce.util.ProductCreator.createValidProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setup(){
        BDDMockito.when(productRepository.findProductByName(anyString())).thenReturn(List.of(createValidProduct()));
        BDDMockito.when(productRepository.saveAndReturn(any(Product.class))).thenReturn(createProductToBeSaved());
        BDDMockito.when(productRepository.findProductById(anyString())).thenReturn(createValidProduct());
    }

    @Test
    @DisplayName("Success - saves and returns a Product")
    void saveAndReturnProduct_WhenSuccessful()  {
        Product created = productService.SaveProduct(createProductToBeSaved());
        assertThat(created.getProductId()).isSameAs(createProductToBeSaved().getProductId());
        Assertions.assertThat(created.getName()).isEqualTo("Smartphone").isNotNull();
        verify(productRepository).saveAndReturn(createProductToBeSaved());
    }

    @Test
    @DisplayName("Success - returns a Product using id ")
    void findProductById_ReturnProduct_WhenSuccessful() {
        String expectedId = createValidProduct().getProductId();
        Product product = productService.findProductById(expectedId);
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getProductId()).isNotNull().isEqualTo(expectedId);
        verify(productRepository).findProductById("321");
    }

    @Test
    @DisplayName("Success - returns a Product using name ")
    void findProductByName_ReturnProduct_WhenSuccessful() {
        String expectedName = createValidProduct().getName();
        List<Product> product = productService.findProductByName(expectedName);
        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.get(0).getName()).isNotNull().isEqualTo(expectedName);
        verify(productRepository).findProductByName("Compiuter");
    }

    @Test
    @DisplayName("Success - removes a Product using id")
    void delete_RemoveProduct_WhenSuccessful(){
        Assertions.assertThatCode(() -> productService.deleteProductById("321")).doesNotThrowAnyException();
        verify(productRepository, times(1)).deleteProductById(createValidProduct().getProductId());
    }


}
