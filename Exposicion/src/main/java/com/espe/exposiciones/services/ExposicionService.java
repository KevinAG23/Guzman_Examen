package com.espe.exposiciones.services;

import com.espe.exposiciones.models.Obra;
import com.espe.exposiciones.models.entities.Exposicion;
import java.util.List;

public interface ExposicionService {
    List<Exposicion> listarTodasLasExposiciones();
    Exposicion obtenerExposicionPorId(Long id);
    Exposicion guardarExposicion(Exposicion exposicion);
    Exposicion actualizarExposicion(Long id, Exposicion exposicion);
    void eliminarExposicion(Long id);

    // Nuevos métodos para la relación con obras
    void asociarObraAExposicion(Long exposicionId, Long obraId);
    List<Obra> listarObrasEnExposicion(Long exposicionId);
    List<Exposicion> listarExposicionesDeObra(Long obraId);
    void removerObraDeExposicion(Long exposicionId, Long obraId);




}