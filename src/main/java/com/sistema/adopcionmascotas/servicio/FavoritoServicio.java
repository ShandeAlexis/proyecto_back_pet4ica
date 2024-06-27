package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.FavoritoDTO;

import java.util.List;

public interface FavoritoServicio {
    FavoritoDTO agregarFavorito(Long usuarioId, Long publicacionId);
    List<FavoritoDTO> obtenerFavoritosPorUsuario(Long usuarioId);
    void eliminarFavorito(Long favoritoId);
}
