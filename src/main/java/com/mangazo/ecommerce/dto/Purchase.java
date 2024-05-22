package com.mangazo.ecommerce.dto;

import com.mangazo.ecommerce.entity.Address;
import com.mangazo.ecommerce.entity.Customer;
import com.mangazo.ecommerce.entity.Order;
import com.mangazo.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;
}
