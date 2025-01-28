package com.espe.exposiciones.repositories;

import com.espe.exposiciones.models.entities.ExposicionObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ExposicionObraRepository extends JpaRepository<ExposicionObra, Long> {
    List<ExposicionObra> findByExposicionId(Long exposicionId);
    List<ExposicionObra> findByObraId(Long obraId);

    @Query("DELETE FROM ExposicionObra eo WHERE eo.exposicionId = :exposicionId AND eo.obraId = :obraId")
    void deleteByExposicionIdAndObraId(@Param("exposicionId") Long exposicionId, @Param("obraId") Long obraId);
}