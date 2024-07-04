package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.CrearPublicacionDTO;
import com.sistema.adopcionmascotas.dto.PublicacionDTO;
import com.sistema.adopcionmascotas.dto.PublicacionRespuesta;

public interface PublicacionServicio {

	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);

	public PublicacionDTO crearPublicacion2(CrearPublicacionDTO crearPublicacionDTO);
	
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina,int medidaDePagina,String ordenarPor,String sortDir);
	
	public PublicacionDTO obtenerPublicacionPorId(long id);
	
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO,long id);

	public void eliminarPublicacion(long id);
	PublicacionRespuesta obtenerPublicacionesPorEspecie(String especie, int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);


}
