package com.sistema.adopcionmascotas.repositorio;

import com.sistema.adopcionmascotas.entidades.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota,Long> {
}
