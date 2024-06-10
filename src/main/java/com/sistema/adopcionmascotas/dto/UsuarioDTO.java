package com.sistema.adopcionmascotas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sistema.adopcionmascotas.entidades.Rol;

import java.util.Set;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String username;
    private String email;

    private String dni;
    private String apellidos;
    private int edad;
    private String sexo;
    private Set<Rol> roles;
    @JsonIgnoreProperties("usuario")
    private Set<PublicacionDTO> publicaciones; // Agregamos el conjunto de PublicacionDTO

    // Constructor

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String username, String email, String dni, String apellidos, int edad, String sexo, Set<Rol> roles, Set<PublicacionDTO> publicaciones) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.dni = dni;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
        this.roles = roles;
        this.publicaciones = publicaciones;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public Set<PublicacionDTO> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Set<PublicacionDTO> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
