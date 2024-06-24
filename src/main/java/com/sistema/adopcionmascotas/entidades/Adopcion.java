package com.sistema.adopcionmascotas.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "adopciones")
public class Adopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    private LocalDateTime fechaSolicitud;
    private String estado; // Pendiente, Aprobada, Rechazada

    public Adopcion() {
    }

    public Adopcion(Usuario usuario, Publicacion publicacion, LocalDateTime fechaSolicitud, String estado) {
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
