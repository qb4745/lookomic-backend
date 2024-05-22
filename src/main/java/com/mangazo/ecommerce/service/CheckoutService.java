package com.mangazo.ecommerce.service;

import com.mangazo.ecommerce.dto.PaymentInfo;
import com.mangazo.ecommerce.dto.Purchase;
import com.mangazo.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}
