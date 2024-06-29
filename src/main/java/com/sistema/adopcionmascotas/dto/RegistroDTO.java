package com.sistema.adopcionmascotas.dto;

public class RegistroDTO {

	private String nombre;
	private String apellidos;
	private String username;
	private String email;
	private String password;
	private String dni;
	private int edad;
	private String sexo;

	//falta implementar esto
	private String imagenPerfilPath;
	private String sobremi;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public String getSobremi() {
		return sobremi;
	}

	public void setSobremi(String sobremi) {
		this.sobremi = sobremi;
	}

	public RegistroDTO() {
		super();
	}

	public String getImagenPerfilPath() {
		return imagenPerfilPath;
	}

	public void setImagenPerfilPath(String imagenPerfilPath) {
		this.imagenPerfilPath = imagenPerfilPath;
	}
}
