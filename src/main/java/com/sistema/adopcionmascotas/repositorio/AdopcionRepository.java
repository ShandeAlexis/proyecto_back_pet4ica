package com.sistema.adopcionmascotas.repositorio;

import com.sistema.adopcionmascotas.entidades.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdopcionRepository extends JpaRepository<Adopcion,Long> {
    boolean existsByUsuarioIdAndPublicacionId(Long usuarioId, Long publicacionId);

    List<Adopcion> findByPublicacionId(Long publicacionId);
}
