package com.mangazo.ecommerce.service;

import com.mangazo.ecommerce.dao.CustomerRepository;
import com.mangazo.ecommerce.dto.PaymentInfo;
import com.mangazo.ecommerce.dto.Purchase;
import com.mangazo.ecommerce.dto.PurchaseResponse;
import com.mangazo.ecommerce.entity.Customer;
import com.mangazo.ecommerce.entity.Order;
import com.mangazo.ecommerce.entity.OrderItem;
import com.stripe.exception.ApiException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
public class CheckoutServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkoutService = new CheckoutServiceImpl(customerRepository, "sk_test_4eC39HqLyjWDarjtT1zdp7dc");
    }

    @Test
    void  testPlaceOrderSuccessfullySavesCustomer() {
        Purchase purchase = new Purchase();
        Order order = new Order();
        purchase.setOrder(order);

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(new OrderItem());
        purchase.setOrderItems(orderItems);

        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        purchase.setCustomer(customer);

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        PurchaseResponse response = checkoutService.placeOrder(purchase);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());
        Customer capturedCustomer = customerCaptor.getValue();

        assertNotNull(response);
        assertNotNull(response.getOrderTrackingNumber());
        assertEquals(1, capturedCustomer.getOrders().size());
    }

    @Test
    void testShouldCreatePaymentIntentWithCorrectId() throws StripeException {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(1000);
        paymentInfo.setCurrency("clp");
        paymentInfo.setReceiptEmail("test@example.com");

        Map<String, Object> params = Map.of(
                "amount", 1000,
                "currency", "clp",
                "payment_method_types", Collections.singletonList("card"),
                "description", "Lookomic.cl",
                "receipt_email", "test@example.com"
        );

        try (MockedStatic<PaymentIntent> mockedPaymentIntent = mockStatic(PaymentIntent.class)) {
            PaymentIntent mockPaymentIntent = mock(PaymentIntent.class);
            when(mockPaymentIntent.getId()).thenReturn("pi_1GqIC8BnB4CC3ZRd9LtXgo2D");
            mockedPaymentIntent.when(() -> PaymentIntent.create(anyMap())).thenReturn(mockPaymentIntent);

            PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

            assertNotNull(paymentIntent);
            assertEquals("pi_1GqIC8BnB4CC3ZRd9LtXgo2D", paymentIntent.getId());
        }
    }

    @Test
    void testShouldThrowStripeExceptionWhenPaymentIntentCreationFails() throws StripeException {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(1000);
        paymentInfo.setCurrency("clp");
        paymentInfo.setReceiptEmail("test@example.com");

        try (MockedStatic<PaymentIntent> ignored = mockStatic(PaymentIntent.class)) {
            when(PaymentIntent.create(anyMap())).thenThrow(new ApiException("Test exception", "Test code", null, null, null));

            assertThrows(StripeException.class, () -> checkoutService.createPaymentIntent(paymentInfo));
        }
    }
}
