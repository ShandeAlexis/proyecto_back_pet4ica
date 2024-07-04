package com.sistema.adopcionmascotas.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CrearPublicacionDTO {
    @NotEmpty
    @Size(min = 3, message = "El titulo debe tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 3, message = "La descripcion debe tener al menos 10 caracteres")
    private String descripcion;

    @NotEmpty
    private String contenido;

    @NotNull(message = "El ID de la mascota no puede ser nulo")
    private Long mascotaId;

    @NotNull(message = "El ID de la mascota no puede ser nulo")
    private Long usuarioId;

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
