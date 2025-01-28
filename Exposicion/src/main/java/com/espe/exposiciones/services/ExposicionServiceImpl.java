package com.espe.exposiciones.services;

import com.espe.exposiciones.clients.ObraClientRest;
import com.espe.exposiciones.models.entities.Exposicion;
import com.espe.exposiciones.models.entities.ExposicionObra;
import com.espe.exposiciones.models.Obra;
import com.espe.exposiciones.repositories.ExposicionObraRepository;
import com.espe.exposiciones.repositories.ExposicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExposicionServiceImpl implements ExposicionService {

    @Autowired
    private ExposicionRepository exposicionRepository;

    @Autowired
    private ExposicionObraRepository exposicionObraRepository;

    @Autowired
    private ObraClientRest obraClientRest;

    @Override
    public List<Exposicion> listarTodasLasExposiciones() {
        return exposicionRepository.findAll();
    }

    @Override
    public Exposicion obtenerExposicionPorId(Long id) {
        return exposicionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exposición no encontrada con ID: " + id));
    }

    @Override
    public Exposicion guardarExposicion(Exposicion exposicion) {
        return exposicionRepository.save(exposicion);
    }

    @Override
    public Exposicion actualizarExposicion(Long id, Exposicion exposicion) {
        Exposicion exposicionExistente = obtenerExposicionPorId(id);
        exposicionExistente.setNombre(exposicion.getNombre());
        exposicionExistente.setDescripcion(exposicion.getDescripcion());
        exposicionExistente.setFechaInicio(exposicion.getFechaInicio());
        exposicionExistente.setFechaFin(exposicion.getFechaFin());
        return exposicionRepository.save(exposicionExistente);
    }

    @Override
    public void eliminarExposicion(Long id) {
        if (!exposicionRepository.existsById(id)) {
            throw new IllegalArgumentException("Exposición no encontrada con ID: " + id);
        }
        exposicionRepository.deleteById(id);
    }

    // Métodos para gestionar la relación entre exposiciones y obras

    public void asociarObraAExposicion(Long exposicionId, Long obraId) {
        ExposicionObra exposicionObra = new ExposicionObra();
        exposicionObra.setExposicionId(exposicionId);
        exposicionObra.setObraId(obraId);
        exposicionObraRepository.save(exposicionObra);
    }

    public List<Obra> listarObrasEnExposicion(Long exposicionId) {
        List<ExposicionObra> relaciones = exposicionObraRepository.findByExposicionId(exposicionId);
        return relaciones.stream()
                .map(relacion -> obraClientRest.obtenerObraPorId(relacion.getObraId()))
                .collect(Collectors.toList());
    }

    public List<Exposicion> listarExposicionesDeObra(Long obraId) {
        List<ExposicionObra> relaciones = exposicionObraRepository.findByObraId(obraId);
        return relaciones.stream()
                .map(relacion -> obtenerExposicionPorId(relacion.getExposicionId()))
                .collect(Collectors.toList());
    }

    public void removerObraDeExposicion(Long exposicionId, Long obraId) {
        exposicionObraRepository.deleteByExposicionIdAndObraId(exposicionId, obraId);
    }
}