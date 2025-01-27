package com.espe.exposiciones.services;

import com.espe.exposiciones.clients.ObraClientRest;
import com.espe.exposiciones.models.Obra;
import com.espe.exposiciones.models.entities.Exposicion;
import com.espe.exposiciones.models.entities.ExposicionObra;
import com.espe.exposiciones.repositories.ExposicionObraRepository;
import com.espe.exposiciones.repositories.ExposicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExposicionServiceImpl implements ExposicionService {

    @Autowired
    private ExposicionRepository repository;

    @Autowired
    private ExposicionObraRepository exposicionObraRepository;

    @Autowired
    private ObraClientRest clientRest;

    @Override
    public List<Exposicion> findAll() {
        List<Exposicion> exposiciones = (List<Exposicion>) repository.findAll();
        for (Exposicion exposicion : exposiciones) {
            List<Obra> obras = new ArrayList<>();
            for (ExposicionObra exposicionObra : exposicion.getExposicionObras()) {
                Optional<Obra> obra = Optional.ofNullable(clientRest.findById(exposicionObra.getObraId()));
                obra.ifPresent(obras::add);
            }
            exposicion.setObras(obras);
        }
        return exposiciones;
    }

    @Override
    public Optional<Exposicion> findById(Long id) {
        Optional<Exposicion> optionalExposicion = repository.findById(id);
        if (optionalExposicion.isPresent()) {
            Exposicion exposicion = optionalExposicion.get();
            List<Obra> obras = new ArrayList<>();
            for (ExposicionObra exposicionObra : exposicion.getExposicionObras()) {
                Optional<Obra> obra = Optional.ofNullable(clientRest.findById(exposicionObra.getObraId()));
                obra.ifPresent(obras::add);
            }
            exposicion.setObras(obras);
        }
        return optionalExposicion;
    }

    @Override
    public Exposicion save(Exposicion exposicion) {
        return repository.save(exposicion);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Obra> addObra(Obra obra, Long id) {
        Optional<Exposicion> optional = repository.findById(id);
        if (optional.isPresent()) {
            Obra obraTemp = clientRest.findById(obra.getId());
            Exposicion exposicion = optional.get();
            ExposicionObra exposicionObra = new ExposicionObra();
            exposicionObra.setObraId(obraTemp.getId());
            exposicionObra = exposicionObraRepository.save(exposicionObra);
            exposicion.addExposicionObra(exposicionObra);
            repository.save(exposicion);
            return Optional.of(obraTemp);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Obra> removeObra(Long obraId, Long exposicionId) {
        Optional<Exposicion> optionalExposicion = repository.findById(exposicionId);
        if (optionalExposicion.isPresent()) {
            Exposicion exposicion = optionalExposicion.get();
            ExposicionObra exposicionObra = exposicion.getExposicionObras().stream()
                    .filter(eo -> eo.getObraId().equals(obraId))
                    .findFirst()
                    .orElse(null);
            if (exposicionObra != null) {
                exposicion.removeExposicionObra(exposicionObra);
                exposicionObraRepository.delete(exposicionObra);
                repository.save(exposicion);
                Obra obraTemp = clientRest.findById(obraId);
                return Optional.of(obraTemp);
            }
        }
        return Optional.empty();
    }
}
