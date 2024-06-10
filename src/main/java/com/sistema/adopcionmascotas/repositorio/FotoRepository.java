package com.sistema.adopcionmascotas.repositorio;

import com.sistema.adopcionmascotas.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto,Long> {
}
