/*
package com.mangazo.ecommerce.dao;

import com.mangazo.ecommerce.entity.Order;
import com.mangazo.ecommerce.entity.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class OrderRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.create.customer}")
    private String sqlCreateCustomer;

    @Value("${sql.script.create.order}")
    private String sqlCreateOrder;



    @AfterEach
    void tearDown() {
        jdbc.execute("DELETE FROM orders");
        jdbc.execute("DELETE FROM address");
        jdbc.execute("DELETE FROM customer");
    }

    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testFindByCustomerEmailOrderByDateCreatedDesc() throws Exception {
        mockMvc.perform(get("/api/orders/search/findByCustomerEmailOrderByDateCreatedDesc")
                        .param("email", "jai.vicencio@duocuc.cl")
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orders", hasSize(2)));
    }
    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrdersGetAll() throws Exception {
        mockMvc.perform(get("/api/orders")
                        .param("page", "0")
                        .param("size", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orders", hasSize(2)));
    }

    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindAll() throws Exception {
        assertNotNull(orderRepository.findAll(), "Orders should be valid");
        assertEquals(2, orderRepository.findAll().size());
    }

    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindById() throws Exception {
        Optional<Order> order = orderRepository.findById(1L);
        assertTrue(order.isPresent());
        assertEquals("tracking123", order.get().getOrderTrackingNumber());
    }

    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderRepositoryFindByIdEmptyResponse() throws Exception {
        Optional<Order> order = orderRepository.findById(0L);
        assertFalse(order.isPresent(), "Order ID = '0' should not be valid");
    }

    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderfindByCustomerEmailOrderByDateCreatedDesc() throws Exception {
        assertNotNull(orderRepository.findByCustomerEmailOrderByDateCreatedDesc("jai.vicencio@duocuc.cl", Pageable.unpaged()), "Order should be valid");
        assertEquals(2, orderRepository.findByCustomerEmailOrderByDateCreatedDesc("jai.vicencio@duocuc.cl", Pageable.unpaged()).getSize());
    }

    @Test
    @Sql({"/insertOrders.sql"})
    @WithMockUser(username = "jai.vicencio@duocuc.cl", roles = "USER")
    void testOrderfindByCustomerEmailOrderByDateCreatedDescEmptyResponse() throws Exception {
        assertEquals(0, orderRepository.findByCustomerEmailOrderByDateCreatedDesc("email.no.registrado@duocuc.cl", Pageable.unpaged()).getSize());
    }



}*/
