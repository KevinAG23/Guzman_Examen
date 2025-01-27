package com.espe.exposiciones.clients;

import com.espe.exposiciones.models.Obra;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estudiantes", url = "http://localhost:8003/api/obras")
public interface ObraClientRest {
    @GetMapping("/{id}")
    Obra findById(@PathVariable Long id);
}
