package com.mangazo.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "region_iso_3166_2")
    private String regionIso31662;

    private String capitalRegional;

    @OneToMany(mappedBy = "region")
    @JsonIgnore
    private List<Comuna> comunas;
}







