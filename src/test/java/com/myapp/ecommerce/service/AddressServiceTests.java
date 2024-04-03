package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Address;
import com.myapp.ecommerce.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.myapp.ecommerce.util.AddressCreator.createAddressToBeSaved;
import static com.myapp.ecommerce.util.AddressCreator.createValidAddress;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setup(){
        BDDMockito.when(addressRepository.findAddressById(anyString())).thenReturn(createValidAddress());
        BDDMockito.when(addressRepository.saveAndReturn(any(Address.class))).thenReturn(createAddressToBeSaved());
    }

    @Test
    @DisplayName("Success - saves and returns a Address")
    void saveAndReturnAddress_WhenSuccessful() {
        Address created = addressService.SaveAddress(createAddressToBeSaved());
        assertThat(created.getAddressId()).isSameAs(createAddressToBeSaved().getAddressId());
        Assertions.assertThat(created.getCity()).isEqualTo("Varginha").isNotNull();
        verify(addressRepository).saveAndReturn(createAddressToBeSaved());
    }

    @Test
    @DisplayName("Success - returns a Address using id ")
    void findAddressById_ReturnAddress_WhenSuccessful()  {
        String expectedId = createValidAddress().getAddressId();
        Address address = addressService.findAddressById(expectedId);
        Assertions.assertThat(address).isNotNull();
        Assertions.assertThat(address.getAddressId()).isNotNull().isEqualTo(expectedId);
        verify(addressRepository).findAddressById("150");
    }

    @Test
    @DisplayName("Success - removes a Address using id")
    void delete_RemoveAddress_WhenSuccessful(){
        Assertions.assertThatCode(() -> addressService.deleteAddressById("150")).doesNotThrowAnyException();
        verify(addressRepository, times(1)).deleteAddressById(createValidAddress().getAddressId());
    }

}
