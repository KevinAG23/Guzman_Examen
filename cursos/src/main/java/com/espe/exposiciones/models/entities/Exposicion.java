package com.espe.exposiciones.models.entities;

import com.espe.exposiciones.models.Obra;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exposiciones")
public class Exposicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;
    private String descripcion;

    @OneToMany
    @JoinColumn
    private List<ExposicionObra> exposicionObras;

    @Transient
    private List<Obra> obras;

    public Exposicion() {
        exposicionObras = new ArrayList<>();
        obras = new ArrayList<>();
    }

    public void addExposicionObra(ExposicionObra exposicionObra) {
        exposicionObras.add(exposicionObra);
    }

    public void removeExposicionObra(ExposicionObra exposicionObra) {
        exposicionObras.remove(exposicionObra);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setExposicionObras(List<ExposicionObra> exposicionObras) {
        this.exposicionObras = exposicionObras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<ExposicionObra> getExposicionObras() {
        return exposicionObras;
    }

    public List<Obra> getObras() {
        return obras;
    }
}
