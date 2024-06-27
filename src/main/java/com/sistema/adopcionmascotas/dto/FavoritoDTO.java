package com.sistema.adopcionmascotas.dto;

import java.time.LocalDateTime;

public class FavoritoDTO {
    private Long id;
    private PublicacionDTO publicacion;
    private LocalDateTime fechaFavorito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PublicacionDTO getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(PublicacionDTO publicacion) {
        this.publicacion = publicacion;
    }

    public LocalDateTime getFechaFavorito() {
        return fechaFavorito;
    }

    public void setFechaFavorito(LocalDateTime fechaFavorito) {
        this.fechaFavorito = fechaFavorito;
    }
}
