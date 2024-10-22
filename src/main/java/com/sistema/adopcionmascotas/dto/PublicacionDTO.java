package com.sistema.adopcionmascotas.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sistema.adopcionmascotas.entidades.Comentario;

public class PublicacionDTO {

	private Long id;
	
	private String titulo;
	
	private String descripcion;
	
	@NotEmpty
	private String contenido;

	private MascotaDTO mascota;

	@JsonIgnoreProperties("publicaciones") // Ignorar publicaciones en el usuario para evitar bucles
	private UsuarioDTO usuario; // Información del usuario asociado a la publicación

	@JsonIgnoreProperties("publicaciones") // Ignorar publicaciones en el usuario para evitar bucles
	private Set<Comentario> comentarios;

	@JsonIgnoreProperties("publicaciones") // Ignorar publicación en adopciones para evitar bucles
	private Set<AdopcionDTO> adopciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}


	public MascotaDTO getMascota() {
		return mascota;
	}

	public void setMascota(MascotaDTO mascota) {
		this.mascota = mascota;
	}

	public PublicacionDTO() {
		super();
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public Set<AdopcionDTO> getAdopciones() {
		return adopciones;
	}

	public void setAdopciones(Set<AdopcionDTO> adopciones) {
		this.adopciones = adopciones;
	}
}
