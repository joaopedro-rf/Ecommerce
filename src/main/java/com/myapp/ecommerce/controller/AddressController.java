package com.myapp.ecommerce.controller;

import com.myapp.ecommerce.entity.Address;
import com.myapp.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Address createAddress(@RequestBody Address address){
        return addressService.SaveAddress(address);
    }

    @GetMapping("/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public Address findAddressById(@PathVariable String addressId){
        return addressService.findAddressById(addressId);
    }

    @DeleteMapping("/delete/{addressId}")
    public void deleteAddressById(@PathVariable String addressId){
        addressService.deleteAddressById(addressId);
    }
}
