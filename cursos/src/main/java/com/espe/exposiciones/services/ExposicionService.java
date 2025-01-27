package com.espe.exposiciones.services;

import com.espe.exposiciones.models.Obra;
import com.espe.exposiciones.models.entities.Exposicion;

import java.util.List;
import java.util.Optional;

public interface ExposicionService {

    List<Exposicion> findAll();

    Optional<Exposicion> findById(Long id);

    Exposicion save(Exposicion exposicion);

    void deleteById(Long id);

    Optional<Obra> addObra(Obra obra, Long id);

    Optional<Obra> removeObra(Long obraId, Long exposicionId);
}
