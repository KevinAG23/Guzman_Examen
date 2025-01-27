package com.espe.exposiciones.repositories;

import com.espe.exposiciones.models.entities.Exposicion;
import org.springframework.data.repository.CrudRepository;

public interface ExposicionRepository extends CrudRepository<Exposicion, Long> {
}
