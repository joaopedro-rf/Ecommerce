package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.repository.CartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.myapp.ecommerce.util.CartCreator.createCartToBeSaved;
import static com.myapp.ecommerce.util.CartCreator.createValidCart;
import static com.myapp.ecommerce.util.OrderCreator.createOrderToBeSaved;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CartServiceTests {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setup(){
        BDDMockito.when(cartRepository.findCartById(anyString())).thenReturn(createValidCart());
        BDDMockito.when(cartRepository.saveAndReturn(any(Cart.class))).thenReturn(createCartToBeSaved());

    }

    @Test
    @DisplayName("Success - saves and returns a Cart")
    void saveAndReturnCart_WhenSuccessful() {
        Cart created = cartService.saveCart(createCartToBeSaved());
        assertThat(created.getCartId()).isSameAs(createCartToBeSaved().getCartId());
        Assertions.assertThat(created.getPrice()).isEqualTo(999.99).isNotNull();
        verify(cartRepository).saveAndReturn(createCartToBeSaved());
    }

    @Test
    @DisplayName("Success - returns a Cart using id ")
    void findCartById_ReturnCart_WhenSuccessful()  {
        String expectedId = createValidCart().getCartId();
        Cart cart = cartService.findCartById(expectedId);
        Assertions.assertThat(cart).isNotNull();
        Assertions.assertThat(cart.getCartId()).isNotNull().isEqualTo(expectedId);
        verify(cartRepository).findCartById("777");
    }

    @Test
    @DisplayName("Success - removes a Cart using id")
    void delete_RemoveOrder_WhenSuccessful(){
        Assertions.assertThatCode(() -> cartService.deleteCartById("777")).doesNotThrowAnyException();
        verify(cartRepository, times(1)).deleteCartById(createValidCart().getCartId());
    }
}
