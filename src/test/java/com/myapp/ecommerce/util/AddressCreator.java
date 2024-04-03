package com.myapp.ecommerce.util;

import com.myapp.ecommerce.entity.Address;

public class AddressCreator {

    public static Address createAddressToBeSaved(){
        return new Address("666","555","Varginha", "Aquela rua la","MG","37014000","999999999","AP-220", true);
    }

    public static Address createValidAddress(){
        return new Address("150","051","Santa Rita do Sapucai", "Aquela rua ali","MG","37010000","111111111","Casa dos fundos", false);
    }
}
