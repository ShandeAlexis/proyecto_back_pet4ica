package com.sistema.adopcionmascotas.entidades;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoritos")
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    private LocalDateTime fechaFavorito;

    public Favorito() {
    }

    public Favorito(Long id, Usuario usuario, Publicacion publicacion, LocalDateTime fechaFavorito) {
        this.id = id;
        this.usuario = usuario;
        this.publicacion = publicacion;
        this.fechaFavorito = fechaFavorito;
    }

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

    public LocalDateTime getFechaFavorito() {
        return fechaFavorito;
    }

    public void setFechaFavorito(LocalDateTime fechaFavorito) {
        this.fechaFavorito = fechaFavorito;
    }
}
