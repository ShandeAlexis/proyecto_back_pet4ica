package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.MascotaDTO;

import java.util.List;

public interface MascotaServicio {
    MascotaDTO crearMascota(MascotaDTO mascotaDTO);
    List<MascotaDTO> obtenerTodasLasMascotas();
    MascotaDTO obtenerMascotaPorId(Long id);
    MascotaDTO actualizarMascota(Long id, MascotaDTO mascotaDTO);
    void eliminarMascota(Long id);
}
