package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.PaymentMethod;
import com.myapp.ecommerce.repository.PaymentMethodRepository;
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

import static com.myapp.ecommerce.util.PaymentMethodCreator.createPaymentToBeSaved;
import static com.myapp.ecommerce.util.PaymentMethodCreator.createValidPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class PaymentMethodServiceTests {

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @InjectMocks
    private PaymentMethodService paymentMethodService;

    @BeforeEach
    void setup() throws ParseException {
        BDDMockito.when(paymentMethodRepository.saveAndReturn(any(PaymentMethod.class))).thenReturn(createPaymentToBeSaved());
        BDDMockito.when(paymentMethodRepository.findPaymentById(anyString())).thenReturn(createValidPayment());
    }

    @Test
    @DisplayName("Success - saves and returns a Payment")
    void saveAndReturnPayment_WhenSuccessful() throws ParseException {
        PaymentMethod created = paymentMethodService.SavePayment(createPaymentToBeSaved());
        assertThat(created.getPaymentMethodId()).isSameAs(createPaymentToBeSaved().getPaymentMethodId());
        Assertions.assertThat(created.getMethod()).isEqualTo("PIX").isNotNull();
        verify(paymentMethodRepository).saveAndReturn(createPaymentToBeSaved());
    }

    @Test
    @DisplayName("Success - returns a Payment using id ")
    void findPaymentById_ReturnPayment_WhenSuccessful() throws ParseException {
        String expectedId = createValidPayment().getPaymentMethodId();
        PaymentMethod paymentMethod = paymentMethodService.findPaymentById(expectedId);
        Assertions.assertThat(paymentMethod).isNotNull();
        Assertions.assertThat(paymentMethod.getPaymentMethodId()).isNotNull().isEqualTo(expectedId);
        verify(paymentMethodRepository).findPaymentById("111");
    }

    @Test
    @DisplayName("Success - removes a Payment using id")
    void delete_RemovePayment_WhenSuccessful() throws ParseException {
        Assertions.assertThatCode(() -> paymentMethodService.deletePaymentById("111")).doesNotThrowAnyException();
        verify(paymentMethodRepository, times(1)).deletePaymentById(createValidPayment().getPaymentMethodId());
    }

}
