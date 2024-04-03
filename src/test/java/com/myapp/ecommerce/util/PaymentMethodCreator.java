package com.myapp.ecommerce.util;

import com.myapp.ecommerce.entity.PaymentMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentMethodCreator {

    public static PaymentMethod createPaymentToBeSaved () throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = dateFormat.parse("2024-03-25T19:15:40.779+00:00");
        return new PaymentMethod("456","123",true,date, "PIX");
    }

    public static PaymentMethod createValidPayment () throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        Date date = dateFormat.parse("2024-03-25T19:15:40.779+00:00");
        return new PaymentMethod("111","222",true,date, "CREDIT_CARD");
    }
}
