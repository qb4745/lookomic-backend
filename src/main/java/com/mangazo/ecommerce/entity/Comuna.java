package com.mangazo.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String codigo;


    @ManyToOne
    @JoinColumn(name ="id_region")
    private Region region;
}
