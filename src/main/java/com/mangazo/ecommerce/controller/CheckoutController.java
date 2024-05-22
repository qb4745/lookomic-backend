package com.mangazo.ecommerce.controller;

import com.mangazo.ecommerce.dto.PaymentInfo;
import com.mangazo.ecommerce.dto.Purchase;
import com.mangazo.ecommerce.dto.PurchaseResponse;
import com.mangazo.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return checkoutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo){
        try {
            String paymentStr = checkoutService.createPaymentIntent(paymentInfo).toJson();
            return new ResponseEntity<>(paymentStr, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
