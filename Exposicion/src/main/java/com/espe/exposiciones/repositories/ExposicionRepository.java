package com.espe.exposiciones.repositories;

import com.espe.exposiciones.models.entities.Exposicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposicionRepository extends JpaRepository<Exposicion, Long> {
}