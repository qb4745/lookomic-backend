package com.mangazo.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String region;

    private String comuna;

    private String street;

    private String zipCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}
