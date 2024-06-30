package com.sistema.adopcionmascotas.entidades;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "detalles_usuario")
public class DetalleUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 3, message = "El sobre mi deben tener al menos 3 caracteres")
    private String sobremi;

    @NotEmpty(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    private String dni;
    @NotEmpty(message = "Los apellidos no pueden estar vacíos")
    @Size(min = 2, message = "Los apellidos deben tener al menos 2 caracteres")
    private String apellidos;
    @Min(value = 18, message = "La edad debe ser al menos 14")
    @Max(value = 100, message = "La edad debe ser como máximo 100")
    private int edad;
    @NotEmpty(message = "El sexo no puede estar vacío")
    @Pattern(regexp = "Masculino|Femenino|No binario", message = "El sexo debe ser 'Masculino' o 'Femenino' o 'No binario'")
    private String sexo;


    public String getSobremi() {
        return sobremi;
    }

    public void setSobremi(String sobremi) {
        this.sobremi = sobremi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
