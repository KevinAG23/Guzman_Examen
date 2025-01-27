package com.espe.exposiciones.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "exposiciones_obras")
public class ExposicionObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "obra_id", unique = true, nullable = false)
    private Long obraId;

    // Getters y Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }

    public Long getId() {
        return id;
    }

    public Long getObraId() {
        return obraId;
    }
}
