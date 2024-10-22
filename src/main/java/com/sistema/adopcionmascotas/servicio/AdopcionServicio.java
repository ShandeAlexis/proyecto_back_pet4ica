package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.AdopcionDTO;

import java.util.List;

public interface AdopcionServicio {
    AdopcionDTO solicitarAdopcion(Long usuarioId, Long publicacionId);
    List<AdopcionDTO> obtenerSolicitudesPorPublicacion(Long publicacionId);

    List<AdopcionDTO> obtenerSolicitudesPorUsuario(Long usuarioId);

    AdopcionDTO aceptarSolicitud(Long adopcionId);

    AdopcionDTO rechazarSolicitud(Long adopcionId);

    void eliminarSolicitud(Long adopcionId);


}
