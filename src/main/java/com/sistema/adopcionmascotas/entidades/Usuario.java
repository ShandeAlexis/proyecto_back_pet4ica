package com.sistema.adopcionmascotas.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotEmpty(message = "El nombre no puede estar vacío")
	@Size(min = 3, message = "El nombre de usuario debe tener al menos 4 caracteres")
	private String nombre;
	@NotEmpty(message = "El nombre de usuario no puede estar vacío")
	@Size(min = 3, message = "El nombre de usuario debe tener al menos 4 caracteres")
	@Pattern(regexp = ".*\\d.*", message = "El nombre de usuario debe contener al menos un dígito")
	private String username;
	@NotEmpty(message = "El email no puede estar vacío")
	@Email(message = "El email debe ser válido")
	private String email;
	@NotEmpty(message = "La contraseña no puede estar vacía")
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private Set<Rol> roles = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detalles_usuario_id", referencedColumnName = "id")
	private DetalleUsuario detalleUsuario;


	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Set<Publicacion> publicaciones = new HashSet<>();



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	// Método para eliminar todos los roles asociados al usuario
	public void eliminarRoles() {
		this.roles.clear();
	}

	public Set<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(Set<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public DetalleUsuario getDetalleUsuario() {
		return detalleUsuario;
	}

	public void setDetalleUsuario(DetalleUsuario detalleUsuario) {
		this.detalleUsuario = detalleUsuario;
	}
}
