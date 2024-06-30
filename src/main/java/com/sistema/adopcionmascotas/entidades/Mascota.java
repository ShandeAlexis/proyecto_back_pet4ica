package com.sistema.adopcionmascotas.entidades;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;
    @NotEmpty(message = "La raza no puede estar vacía")
    @Size(min = 2, message = "La raza debe tener al menos 2 caracteres")
    private String raza;
    @Min(value = 0, message = "La edad debe ser un valor positivo")
    private int edad;

    @NotEmpty(message = "La imagen no puede estar vacía")
    private String imagenPath; // Nueva propiedad para la ruta de la imagen


    private String especie;

    // Constructores, getters y setters

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String raza, int edad, String especie) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.especie= especie;
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }
}
