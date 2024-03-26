package com.myapp.ecommerce.service;

import com.myapp.ecommerce.entity.Address;
import com.myapp.ecommerce.entity.Cart;
import com.myapp.ecommerce.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address SaveAddress(Address address){
        return addressRepository.saveAndReturn(address);
    }

    public Address findAddressById(String addressId){
        return addressRepository.load(Address.class, addressId);
    }
    public void deleteAddressById(String addressId){
        addressRepository.delete(this.findAddressById(addressId));
    }

}
