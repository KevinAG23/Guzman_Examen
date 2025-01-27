package com.espe.exposiciones.clients;

import com.espe.exposiciones.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estudiantes", url = "http://localhost:8003/api/estudiantes")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    Usuario findById(@PathVariable Long id);
}

