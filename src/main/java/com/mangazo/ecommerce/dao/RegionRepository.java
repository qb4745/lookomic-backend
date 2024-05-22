package com.mangazo.ecommerce.dao;


import com.mangazo.ecommerce.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "regiones", path="regiones")
public interface RegionRepository extends JpaRepository<Region, Integer> {

    
}
