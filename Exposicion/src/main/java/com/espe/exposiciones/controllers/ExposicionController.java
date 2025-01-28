package com.espe.exposiciones.controllers;

import com.espe.exposiciones.models.Obra;
import com.espe.exposiciones.models.entities.Exposicion;
import com.espe.exposiciones.services.ExposicionService;
import com.espe.exposiciones.services.ExposicionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exposiciones")
public class ExposicionController {

    @Autowired
    private ExposicionService exposicionService;

    // Listar todas las exposiciones
    @GetMapping
    public ResponseEntity<List<Exposicion>> listarExposiciones() {
        return ResponseEntity.ok(exposicionService.listarTodasLasExposiciones());
    }

    // Obtener una exposición por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerExposicionPorId(@PathVariable Long id) {
        try {
            Exposicion exposicion = exposicionService.obtenerExposicionPorId(id);
            return ResponseEntity.ok(exposicion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear una nueva exposición
    @PostMapping
    public ResponseEntity<?> guardarExposicion(@Valid @RequestBody Exposicion exposicion, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validarErrores(result));
        }
        Exposicion exposicionCreada = exposicionService.guardarExposicion(exposicion);
        return ResponseEntity.ok(exposicionCreada);
    }

    // Actualizar una exposición existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarExposicion(@PathVariable Long id, @Valid @RequestBody Exposicion exposicion, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validarErrores(result));
        }
        try {
            Exposicion exposicionActualizada = exposicionService.actualizarExposicion(id, exposicion);
            return ResponseEntity.ok(exposicionActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar una exposición
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarExposicion(@PathVariable Long id) {
        try {
            exposicionService.eliminarExposicion(id);
            return ResponseEntity.ok(Map.of("mensaje", "Exposición eliminada con éxito"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Método para manejar errores de validación
    private Map<String, String> validarErrores(BindingResult result) {
        return result.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage()
                ));
    }




    // Asociar una obra a una exposición
    @PostMapping("/{exposicionId}/obras/{obraId}")
    public ResponseEntity<?> asociarObraAExposicion(@PathVariable Long exposicionId, @PathVariable Long obraId) {
        exposicionService.asociarObraAExposicion(exposicionId, obraId);
        return ResponseEntity.ok(Map.of("mensaje", "Obra asociada a la exposición con éxito"));
    }

    // Listar las obras en una exposición
    @GetMapping("/{exposicionId}/obras")
    public ResponseEntity<List<Obra>> listarObrasEnExposicion(@PathVariable Long exposicionId) {
        return ResponseEntity.ok(exposicionService.listarObrasEnExposicion(exposicionId));
    }

    // Listar las exposiciones donde se mostró una obra
    @GetMapping("/obras/{obraId}/exposiciones")
    public ResponseEntity<List<Exposicion>> listarExposicionesDeObra(@PathVariable Long obraId) {
        return ResponseEntity.ok(exposicionService.listarExposicionesDeObra(obraId));
    }

    // Remover una obra de una exposición
    @DeleteMapping("/{exposicionId}/obras/{obraId}")
    public ResponseEntity<?> removerObraDeExposicion(@PathVariable Long exposicionId, @PathVariable Long obraId) {
        exposicionService.removerObraDeExposicion(exposicionId, obraId);
        return ResponseEntity.ok(Map.of("mensaje", "Obra removida de la exposición con éxito"));
    }
}
