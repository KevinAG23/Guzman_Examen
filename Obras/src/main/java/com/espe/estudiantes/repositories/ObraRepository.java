package com.espe.estudiantes.repositories;


import com.espe.estudiantes.models.entities.Obra;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ObraRepository extends JpaRepository<Obra, Long> {
}

