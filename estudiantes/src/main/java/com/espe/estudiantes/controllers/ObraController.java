package com.espe.estudiantes.controllers;

import com.espe.estudiantes.models.entities.Obra;
import com.espe.estudiantes.services.ObraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    // Listar todas las obras
    @GetMapping
    public ResponseEntity<List<Obra>> listarObras() {
        return ResponseEntity.ok(obraService.listarTodasLasObras());
    }

    // Obtener una obra por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerObraPorId(@PathVariable Long id) {
        try {
            Obra obra = obraService.obtenerObraPorId(id);
            return ResponseEntity.ok(obra);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Crear una nueva obra
    @PostMapping
    public ResponseEntity<?> guardarObra(@Valid @RequestBody Obra obra, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validarErrores(result));
        }
        Obra obraCreada = obraService.guardarObra(obra);
        return ResponseEntity.ok(obraCreada);
    }

    // Actualizar una obra existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarObra(@PathVariable Long id, @Valid @RequestBody Obra obra, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validarErrores(result));
        }
        try {
            Obra obraActualizada = obraService.actualizarObra(id, obra);
            return ResponseEntity.ok(obraActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Eliminar una obra
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarObra(@PathVariable Long id) {
        try {
            obraService.eliminarObra(id);
            return ResponseEntity.ok(Map.of("mensaje", "Obra eliminada con éxito"));
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
}
