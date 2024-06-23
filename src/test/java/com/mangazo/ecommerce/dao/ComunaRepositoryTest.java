/*
package com.mangazo.ecommerce.dao;
import com.mangazo.ecommerce.dao.ComunaRepository;
import com.mangazo.ecommerce.entity.Comuna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ComunaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ComunaRepository comunaRepository;

    @BeforeEach
    public void setUp() {
        Comuna comuna1 = new Comuna();
        comuna1.setRegionId(1);
        entityManager.persist(comuna1);

        Comuna comuna2 = new Comuna();
        comuna2.setRegionId(2);
        entityManager.persist(comuna2);

        entityManager.flush();
    }

    @Test
    @DisplayName("Find by region id returns correct comunas")
    public void findByRegionIdReturnsCorrectComunas() {
        List<Comuna> foundComunas = comunaRepository.findByRegionId(1);

        assertThat(foundComunas).hasSize(1);
        assertThat(foundComunas.get(0).getRegionId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Find by region id returns empty list for non-existent region id")
    public void findByRegionIdReturnsEmptyListForNonExistentRegionId() {
        List<Comuna> foundComunas = comunaRepository.findByRegionId(999);

        assertThat(foundComunas).isEmpty();
    }
}
*/
