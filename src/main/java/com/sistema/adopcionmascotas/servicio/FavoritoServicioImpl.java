package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.FavoritoDTO;
import com.sistema.adopcionmascotas.entidades.Favorito;
import com.sistema.adopcionmascotas.entidades.Publicacion;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.excepciones.ResourceNotFoundException;
import com.sistema.adopcionmascotas.excepciones.SolicitudDuplicadaException;
import com.sistema.adopcionmascotas.repositorio.FavoritoRepository;
import com.sistema.adopcionmascotas.repositorio.PublicacionRepositorio;
import com.sistema.adopcionmascotas.repositorio.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritoServicioImpl implements FavoritoServicio{
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Autowired
    private PublicacionRepositorio publicacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FavoritoDTO agregarFavorito(Long usuarioId, Long publicacionId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        boolean existe= favoritoRepository.existsByUsuarioIdAndPublicacionId(usuarioId,publicacionId);
        if (existe){
            throw new SolicitudDuplicadaException("Ya existe este publicacion como favorito");
        }



        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setPublicacion(publicacion);
        favorito.setFechaFavorito(LocalDateTime.now());

        Favorito favoritoGuardado = favoritoRepository.save(favorito);

        return mapearDTO(favoritoGuardado);
    }

    @Override
    public List<FavoritoDTO> obtenerFavoritosPorUsuario(Long usuarioId) {
        List<Favorito> favoritos = favoritoRepository.findByUsuarioId(usuarioId);
        return favoritos.stream().map(this::mapearDTO).collect(Collectors.toList());
    }

    @Override
    public void eliminarFavorito(Long favoritoId) {
        Favorito favorito = favoritoRepository.findById(favoritoId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorito", "id", favoritoId));
        favoritoRepository.delete(favorito);
    }

    private FavoritoDTO mapearDTO(Favorito favorito) {
        return modelMapper.map(favorito, FavoritoDTO.class);
    }
}
