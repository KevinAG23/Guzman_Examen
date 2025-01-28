package com.espe.exposiciones.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "exposicion_obra")
public class ExposicionObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exposicion_id", nullable = false)
    private Long exposicionId;

    @Column(name = "obra_id", nullable = false)
    private Long obraId;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExposicionId() {
        return exposicionId;
    }

    public void setExposicionId(Long exposicionId) {
        this.exposicionId = exposicionId;
    }

    public Long getObraId() {
        return obraId;
    }

    public void setObraId(Long obraId) {
        this.obraId = obraId;
    }
}