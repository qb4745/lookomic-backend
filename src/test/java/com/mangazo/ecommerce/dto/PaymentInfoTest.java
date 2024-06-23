package com.mangazo.ecommerce.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentInfoTest {

    @Test
    void testPaymentInfoSettersAndGetters() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(1000);
        paymentInfo.setCurrency("CLP");
        paymentInfo.setReceiptEmail("test@example.com");

        assertEquals(1000, paymentInfo.getAmount());
        assertEquals("CLP", paymentInfo.getCurrency());
        assertEquals("test@example.com", paymentInfo.getReceiptEmail());
    }

    @Test
    void testPaymentInfoEqualsAndHashCode() {
        PaymentInfo paymentInfo1 = new PaymentInfo();
        paymentInfo1.setAmount(1000);
        paymentInfo1.setCurrency("CLP");
        paymentInfo1.setReceiptEmail("test@example.com");

        PaymentInfo paymentInfo2 = new PaymentInfo();
        paymentInfo2.setAmount(1000);
        paymentInfo2.setCurrency("CLP");
        paymentInfo2.setReceiptEmail("test@example.com");

        assertEquals(paymentInfo1, paymentInfo2);
        assertEquals(paymentInfo1.hashCode(), paymentInfo2.hashCode());
    }

    @Test
    void testPaymentInfoToString() {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(1000);
        paymentInfo.setCurrency("CLP");
        paymentInfo.setReceiptEmail("test@example.com");

        String expectedString = "PaymentInfo(amount=1000, currency=CLP, receiptEmail=test@example.com)";
        assertEquals(expectedString, paymentInfo.toString());
    }
}
