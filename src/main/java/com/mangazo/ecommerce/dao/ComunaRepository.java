package com.mangazo.ecommerce.dao;

import com.mangazo.ecommerce.entity.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    List<Comuna> findByRegionId(@Param("id") Integer id);
}
