package com.sistema.adopcionmascotas.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo" }) })
public class Publicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "contenido", nullable = false)
	private String contenido;
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@JsonManagedReference
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Adopcion> adopciones = new HashSet<>();

	@JsonManagedReference
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comentario> comentarios = new HashSet<>();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "mascota_id", referencedColumnName = "id")
	private Mascota mascota;

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

	public Mascota getMascota() {
		return mascota;
	}

	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}

	public Publicacion() {
		super();
	}

	public Publicacion(Long id, String titulo, String descripcion, String contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Adopcion> getAdopciones() {
		return adopciones;
	}

	public void setAdopciones(Set<Adopcion> adopciones) {
		this.adopciones = adopciones;
	}
}
