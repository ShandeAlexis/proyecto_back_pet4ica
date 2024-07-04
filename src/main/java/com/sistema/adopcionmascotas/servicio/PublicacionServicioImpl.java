package com.sistema.adopcionmascotas.servicio;

import java.util.List;
import java.util.stream.Collectors;

import com.sistema.adopcionmascotas.dto.CrearPublicacionDTO;
import com.sistema.adopcionmascotas.dto.UsuarioDTO;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.repositorio.UsuarioRepositorio;
import com.sistema.adopcionmascotas.seguridad.JwtTokenProvider;
import io.jsonwebtoken.Jwt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sistema.adopcionmascotas.dto.PublicacionDTO;
import com.sistema.adopcionmascotas.dto.PublicacionRespuesta;
import com.sistema.adopcionmascotas.entidades.Publicacion;
import com.sistema.adopcionmascotas.entidades.Mascota;
import com.sistema.adopcionmascotas.excepciones.ResourceNotFoundException;
import com.sistema.adopcionmascotas.repositorio.MascotaRepositorio;
import com.sistema.adopcionmascotas.repositorio.PublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements PublicacionServicio {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PublicacionRepositorio publicacionRepositorio;

	@Autowired
	private MascotaRepositorio mascotaRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;



	@Override
	public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
		Publicacion publicacion = mapearEntidad(publicacionDTO);

		if (publicacionDTO.getMascota() != null) {
			Mascota mascota = mascotaRepositorio.findById(publicacionDTO.getMascota().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Mascota", "id", publicacionDTO.getMascota().getId()));
			publicacion.setMascota(mascota);
		}

		if (publicacionDTO.getUsuario() != null) {
			Usuario usuario = usuarioRepositorio.findById(publicacionDTO.getUsuario().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", publicacionDTO.getUsuario().getId()));
			publicacion.setUsuario(usuario);
		}

		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
		return mapearDTO(nuevaPublicacion);
	}


	@Override
	public PublicacionDTO crearPublicacion2(CrearPublicacionDTO crearPublicacionDTO) {
		Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(crearPublicacionDTO.getTitulo());
		publicacion.setDescripcion(crearPublicacionDTO.getDescripcion());
		publicacion.setContenido(crearPublicacionDTO.getContenido());

		Mascota mascota = mascotaRepositorio.findById(crearPublicacionDTO.getMascotaId())
				.orElseThrow(() -> new ResourceNotFoundException("Mascota", "id", crearPublicacionDTO.getMascotaId()));
		publicacion.setMascota(mascota);

		Usuario usuario = usuarioRepositorio.findById(crearPublicacionDTO.getUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", crearPublicacionDTO.getUsuarioId()));
		publicacion.setUsuario(usuario);

		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
		return modelMapper.map(nuevaPublicacion, PublicacionDTO.class);
	}



	@Override
	public PublicacionRespuesta obtenerTodasLasPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);

		List<PublicacionDTO> contenido = publicaciones.getContent().stream().map(this::mapearDTO)
				.collect(Collectors.toList());

		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());

		return publicacionRespuesta;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		return mapearDTO(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());

		if (publicacionDTO.getMascota() != null) {
			Mascota mascota = mascotaRepositorio.findById(publicacionDTO.getMascota().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Mascota", "id", publicacionDTO.getMascota().getId()));
			publicacion.setMascota(mascota);
		}

		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
		return mapearDTO(publicacionActualizada);
	}

	@Override
	public void eliminarPublicacion(long id) {
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		publicacionRepositorio.delete(publicacion);
	}

	@Override
	public PublicacionRespuesta obtenerPublicacionesPorEspecie(String especie, int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();
		Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

		Page<Publicacion> publicaciones = publicacionRepositorio.findByMascotaEspecie(especie, pageable);

		List<PublicacionDTO> contenido = publicaciones.getContent().stream().map(this::mapearDTO)
				.collect(Collectors.toList());

		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());

		return publicacionRespuesta;
	}




	// Convierte entidad a DTO
	private PublicacionDTO mapearDTO(Publicacion publicacion) {
		return modelMapper.map(publicacion, PublicacionDTO.class);
	}

	// Convierte de DTO a Entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		return modelMapper.map(publicacionDTO, Publicacion.class);
	}
}
