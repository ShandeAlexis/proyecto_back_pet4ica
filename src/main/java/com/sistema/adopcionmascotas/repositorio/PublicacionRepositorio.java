package com.sistema.adopcionmascotas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.adopcionmascotas.entidades.Publicacion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepositorio extends  JpaRepository<Publicacion, Long>{

}
