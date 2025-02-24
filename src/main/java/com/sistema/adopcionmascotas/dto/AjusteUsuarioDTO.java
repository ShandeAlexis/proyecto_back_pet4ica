package com.sistema.adopcionmascotas.dto;

import com.sistema.adopcionmascotas.entidades.Rol;

import java.util.Set;

public class AjusteUsuarioDTO {
    private Long id;
    private String nombre;
    private String username;
    private String email;
    private String imagenPerfilPath;
    private String sobremi;
    private String dni;
    private String apellidos;
    private int edad;
    private String sexo;

    private int telefono;
    private Set<Rol> roles;

    public AjusteUsuarioDTO() {
    }

    public AjusteUsuarioDTO(Long id, String nombre, String username, String email, String imagenPerfilPath, String sobremi, String dni, String apellidos, int edad, String sexo, int telefono, Set<Rol> roles) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.imagenPerfilPath = imagenPerfilPath;
        this.sobremi = sobremi;
        this.dni = dni;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
        this.telefono = telefono;
        this.roles = roles;
    }

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

    public String getImagenPerfilPath() {
        return imagenPerfilPath;
    }

    public void setImagenPerfilPath(String imagenPerfilPath) {
        this.imagenPerfilPath = imagenPerfilPath;
    }

    public String getSobremi() {
        return sobremi;
    }

    public void setSobremi(String sobremi) {
        this.sobremi = sobremi;
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

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
