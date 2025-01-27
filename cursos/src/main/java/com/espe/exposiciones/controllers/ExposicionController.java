package com.espe.exposiciones.controllers;

import com.espe.exposiciones.models.Obra;
import com.espe.exposiciones.models.entities.Exposicion;
import com.espe.exposiciones.services.ExposicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exposiciones")
public class ExposicionController {

    @Autowired
    private ExposicionService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Exposicion exposicion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(exposicion));
    }

    @GetMapping
    public ResponseEntity<List<Exposicion>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Exposicion> exposicion = service.findById(id);
        if (exposicion.isPresent()) {
            return ResponseEntity.ok(exposicion.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exposición no encontrada");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Exposicion exposicion, @PathVariable Long id) {
        Optional<Exposicion> optionalExposicion = service.findById(id);
        if (optionalExposicion.isPresent()) {
            Exposicion exposicionActualizada = optionalExposicion.get();
            exposicionActualizada.setNombre(exposicion.getNombre());
            exposicionActualizada.setDescripcion(exposicion.getDescripcion());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(exposicionActualizada));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exposición no encontrada");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Exposicion> exposicion = service.findById(id);
        if (exposicion.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exposición no encontrada");
        }
    }

    @PostMapping("/{id}/obras")
    public ResponseEntity<?> agregarObra(@RequestBody Obra obra, @PathVariable Long id) {
        Optional<Obra> optional;
        try {
            optional = service.addObra(obra, id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
    }

    @DeleteMapping("/{exposicionId}/obras/{obraId}")
    public ResponseEntity<?> removeObra(@PathVariable Long exposicionId, @PathVariable Long obraId) {
        Optional<Obra> optionalObra;
        try {
            optionalObra = service.removeObra(obraId, exposicionId);
            if (optionalObra.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(optionalObra.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "Obra no encontrada en la exposición"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
