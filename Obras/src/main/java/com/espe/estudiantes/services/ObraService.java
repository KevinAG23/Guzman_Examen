package com.espe.estudiantes.services;

import java.util.List;

import com.espe.estudiantes.models.entities.Obra;

public interface ObraService {

    List<Obra> listarTodasLasObras();
    Obra obtenerObraPorId(Long id);
    Obra guardarObra(Obra obra);
    Obra actualizarObra(Long id, Obra obra);
    void eliminarObra(Long id);
}
