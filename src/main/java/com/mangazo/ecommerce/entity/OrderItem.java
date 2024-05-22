package com.mangazo.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String quantity;

    private int unitPrice;

    private Long productId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
