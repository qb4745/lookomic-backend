package com.mangazo.ecommerce.service;

import com.mangazo.ecommerce.dao.CustomerRepository;
import com.mangazo.ecommerce.dto.PaymentInfo;
import com.mangazo.ecommerce.dto.Purchase;
import com.mangazo.ecommerce.dto.PurchaseResponse;
import com.mangazo.ecommerce.entity.Customer;
import com.mangazo.ecommerce.entity.Order;
import com.mangazo.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;

        Stripe.apiKey = secretKey;
    }


    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // traer la info de la order desde el dto.
        Order order = purchase.getOrder();

        // generar numero de pedido.
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // popular la order con los orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // popular order con ambas Address
        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());

        // popular customer con order
        Customer customer = purchase.getCustomer();

        String email = customer.getEmail();

        //Customer customerExistInDB = customerRepository.findByEmail(email);
        Optional<Customer> customerExistInDB = customerRepository.findByEmail(email);

        if(customerExistInDB.isPresent()) {
            customer = customerExistInDB.get();
        }


        customer.add(order);

        // guardar en db
        customerRepository.save(customer);

        // regresar una respuesta
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = Collections.singletonList("card");

        Map<String, Object> params = Map.of(
                "amount", paymentInfo.getAmount(),
                "currency", paymentInfo.getCurrency(),
                "payment_method_types", paymentMethodTypes,
                "description", "Lookomic.cl",
                "receipt_email", paymentInfo.getReceiptEmail()
        );

        return PaymentIntent.create(params);
    }

        private String generateOrderTrackingNumber() {
            return UUID.randomUUID().toString();
        }

}
