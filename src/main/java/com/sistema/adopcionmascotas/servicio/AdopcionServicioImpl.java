package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.AdopcionDTO;
import com.sistema.adopcionmascotas.entidades.Adopcion;
import com.sistema.adopcionmascotas.entidades.Publicacion;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.excepciones.ResourceNotFoundException;
import com.sistema.adopcionmascotas.excepciones.SolicitudDuplicadaException;
import com.sistema.adopcionmascotas.repositorio.AdopcionRepository;
import com.sistema.adopcionmascotas.repositorio.PublicacionRepositorio;
import com.sistema.adopcionmascotas.repositorio.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdopcionServicioImpl implements AdopcionServicio{
    @Autowired
    private AdopcionRepository adopcionRepository;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PublicacionRepositorio publicacionRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public AdopcionDTO solicitarAdopcion(Long usuarioId, Long publicacionId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Verificar si ya existe una solicitud de adopción para el mismo usuario y publicación
        boolean exists = adopcionRepository.existsByUsuarioIdAndPublicacionId(usuarioId, publicacionId);
        if (exists) {
            throw new SolicitudDuplicadaException("Ya existe una solicitud de adopción para este usuario y publicación.");
        }

        Adopcion adopcion = new Adopcion(usuario, publicacion, LocalDateTime.now(), "Pendiente");
        adopcion = adopcionRepository.save(adopcion);
        return mapearDTO(adopcion);
    }

    @Override
    public List<AdopcionDTO> obtenerSolicitudesPorPublicacion(Long publicacionId) {
        List<Adopcion> adopciones = adopcionRepository.findByPublicacionId(publicacionId);
        return adopciones.stream().map(this::mapearDTO).collect(Collectors.toList());
    }

    @Override
    public AdopcionDTO aceptarSolicitud(Long adopcionId) {
        Adopcion adopcion = adopcionRepository.findById(adopcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Adopcion","id",adopcionId));

        adopcion.setEstado("Aceptado");
        adopcion = adopcionRepository.save(adopcion);
        return mapearDTO(adopcion);
    }

    @Override
    public AdopcionDTO rechazarSolicitud(Long adopcionId) {
        Adopcion adopcion = adopcionRepository.findById(adopcionId)
                .orElseThrow(() -> new ResourceNotFoundException("Adopcion","id",adopcionId));

        adopcion.setEstado("Rechazado");
        adopcion = adopcionRepository.save(adopcion);
        return mapearDTO(adopcion);
    }

    private AdopcionDTO mapearDTO(Adopcion adopcion) {
        return modelMapper.map(adopcion, AdopcionDTO.class);
    }

    private Adopcion mapearEntidad(AdopcionDTO adopcionDTO) {
        return modelMapper.map(adopcionDTO, Adopcion.class);
    }
}
