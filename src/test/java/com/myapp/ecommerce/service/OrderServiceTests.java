package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Order;
import com.myapp.ecommerce.entity.PaymentMethod;
import com.myapp.ecommerce.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;

import static com.myapp.ecommerce.util.OrderCreator.createOrderToBeSaved;
import static com.myapp.ecommerce.util.OrderCreator.createValidOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup(){
        BDDMockito.when(orderRepository.findOrderById(anyString())).thenReturn(createValidOrder());
        BDDMockito.when(orderRepository.saveAndReturn(any(Order.class))).thenReturn(createOrderToBeSaved());
    }

    @Test
    @DisplayName("Success - saves and returns a Order")
    void saveAndReturnOrder_WhenSuccessful() throws ParseException {
        Order created = orderService.SaveOrder(createOrderToBeSaved());
        assertThat(created.getOrderId()).isSameAs(createOrderToBeSaved().getOrderId());
        Assertions.assertThat(created.getStatus()).isEqualTo("Shipped").isNotNull();
        verify(orderRepository).saveAndReturn(createOrderToBeSaved());
    }

    @Test
    @DisplayName("Success - returns a Order using id ")
    void findOrderById_ReturnOrder_WhenSuccessful() throws ParseException {
        String expectedId = createValidOrder().getOrderId();
        Order order = orderService.findOrderById(expectedId);
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(order.getOrderId()).isNotNull().isEqualTo(expectedId);
        verify(orderRepository).findOrderById("222");
    }

    @Test
    @DisplayName("Success - removes a Order using id")
    void delete_RemoveOrder_WhenSuccessful(){
        Assertions.assertThatCode(() -> orderService.deleteOrderById("222")).doesNotThrowAnyException();
        verify(orderRepository, times(1)).deleteOrderById(createValidOrder().getOrderId());
    }
}
