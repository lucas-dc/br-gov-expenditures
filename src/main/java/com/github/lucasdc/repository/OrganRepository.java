package com.github.lucasdc.repository;

import com.github.lucasdc.entity.Organ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganRepository extends JpaRepository<Organ, Long> {

    Optional<Organ> findByCode(String code);
}
