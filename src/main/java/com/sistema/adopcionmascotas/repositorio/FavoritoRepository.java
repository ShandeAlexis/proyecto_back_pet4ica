package com.sistema.adopcionmascotas.repositorio;

import com.sistema.adopcionmascotas.entidades.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    boolean existsByUsuarioIdAndPublicacionId(Long usuarioId, Long publicacionId);

    List<Favorito> findByUsuarioId(Long usuarioId);
}
