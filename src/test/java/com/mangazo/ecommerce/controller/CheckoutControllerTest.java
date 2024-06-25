package com.mangazo.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangazo.ecommerce.dao.CustomerRepository;
import com.mangazo.ecommerce.dto.PaymentInfo;
import com.mangazo.ecommerce.dto.Purchase;
import com.mangazo.ecommerce.dto.PurchaseResponse;
import com.mangazo.ecommerce.entity.Customer;
import com.mangazo.ecommerce.entity.Order;
import com.mangazo.ecommerce.entity.OrderItem;
import com.mangazo.ecommerce.service.CheckoutService;
import com.mangazo.ecommerce.service.CheckoutServiceImpl;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CheckoutServiceImpl mockCheckoutService;



    @Mock
    private CheckoutService mockCheckoutServiceNoImpl;

    @Value("${stripe.key.secret}")
    private String stripeSecretKey;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Stripe.apiKey = stripeSecretKey;
    }

    @Test
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    @DisplayName("Should return purchase response when place order is called")
    public void shouldReturnPurchaseResponseWhenPlaceOrderIsCalled() throws Exception {
        Purchase mockPurchase = createMockPurchase();

        when(mockCheckoutService.placeOrder(mockPurchase)).thenAnswer(invocation -> new PurchaseResponse(UUID.randomUUID().toString()));
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(mockPurchase.getCustomer()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/checkout/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPurchase)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderTrackingNumber", matchesPattern("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}")));
    }

    private Purchase createMockPurchase() {
        Purchase mockPurchase = new Purchase();
        Order mockOrder = new Order();
        mockPurchase.setOrder(mockOrder);
        Set<OrderItem> mockOrderItems = new HashSet<>();
        mockPurchase.setOrderItems(mockOrderItems);
        Customer mockCustomer = new Customer();
        mockCustomer.setEmail("test@example.com");
        mockPurchase.setCustomer(mockCustomer);
        return mockPurchase;
    }



    @Test
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    @DisplayName("Should return payment intent when create payment intent is called")
    public void shouldReturnPaymentIntentWhenCreatePaymentIntentIsCalled() throws Exception {
        // Arrange
        PaymentInfo mockPaymentInfo = new PaymentInfo();
        mockPaymentInfo.setAmount(1000);
        mockPaymentInfo.setCurrency("clp");
        mockPaymentInfo.setReceiptEmail("test@example.com");

        // Mock the PaymentIntent and its toJson method
        PaymentIntent mockPaymentIntent = Mockito.mock(PaymentIntent.class);
        when(mockPaymentIntent.toJson()).thenReturn("{}");

        // Mock the behavior of mockCheckoutService.createPaymentIntent() method
        doReturn(mockPaymentIntent).when(mockCheckoutServiceNoImpl).createPaymentIntent(mockPaymentInfo);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/checkout/payment-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPaymentInfo)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

    @Test
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    @DisplayName("Should return payment intent when create payment intent is called")
    public void shouldReturnUnautorizedWhenCreatePaymentIntentIsCalledWithoutAuth() throws Exception {
        // Arrange
        PaymentInfo mockPaymentInfo = new PaymentInfo();
        mockPaymentInfo.setAmount(1000);
        mockPaymentInfo.setCurrency("clp");
        mockPaymentInfo.setReceiptEmail("test@example.com");

        // Mock the PaymentIntent and its toJson method
        PaymentIntent mockPaymentIntent = Mockito.mock(PaymentIntent.class);
        when(mockPaymentIntent.toJson()).thenReturn("{}");

        // Mock the behavior of mockCheckoutService.createPaymentIntent() method
        doReturn(mockPaymentIntent).when(mockCheckoutServiceNoImpl).createPaymentIntent(mockPaymentInfo);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/checkout/payment-intent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockPaymentInfo)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }

}