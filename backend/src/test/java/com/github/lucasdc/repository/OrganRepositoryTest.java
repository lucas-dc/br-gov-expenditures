package com.github.lucasdc.repository;

import com.github.lucasdc.entity.Branch;
import com.github.lucasdc.entity.Organ;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrganRepositoryTest {

    @Autowired
    private OrganRepository organRepository;

    @Test
    void shouldFindOrganByCode() {
        Optional<Organ> result = organRepository.findByCode("20000");

        assertThat(result).isPresent();
        assertEquals("20000", result.get().getCode());
        assertEquals("PRESIDENCIA DA REPUBLICA", result.get().getName());
        assertEquals(Branch.EXECUTIVE, result.get().getBranch());
    }
}
