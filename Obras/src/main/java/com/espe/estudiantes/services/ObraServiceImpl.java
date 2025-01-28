package com.espe.estudiantes.services;

import com.espe.estudiantes.models.entities.Obra;
import com.espe.estudiantes.repositories.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObraServiceImpl implements ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Override
    public List<Obra> listarTodasLasObras() {
        return obraRepository.findAll();
    }

    @Override
    public Obra obtenerObraPorId(Long id) {
        return obraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Obra no encontrada con ID: " + id));
    }

    @Override
    public Obra guardarObra(Obra obra) {
        return obraRepository.save(obra);
    }

    @Override
    public Obra actualizarObra(Long id, Obra obra) {
        Obra obraExistente = obtenerObraPorId(id);
        obraExistente.setTitulo(obra.getTitulo());
        obraExistente.setAutor(obra.getAutor());
        obraExistente.setDescripcion(obra.getDescripcion());
        return obraRepository.save(obraExistente);
    }

    @Override
    public void eliminarObra(Long id) {
        if (!obraRepository.existsById(id)) {
            throw new IllegalArgumentException("Obra no encontrada con ID: " + id);
        }
        obraRepository.deleteById(id);
    }
}