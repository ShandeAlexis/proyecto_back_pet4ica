package com.sistema.adopcionmascotas.controlador;

import com.sistema.adopcionmascotas.dto.AdopcionDTO;
import com.sistema.adopcionmascotas.dto.AdopcionSolicitudDTO;
import com.sistema.adopcionmascotas.entidades.Adopcion;
import com.sistema.adopcionmascotas.servicio.AdopcionServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adopciones")
public class AdopcionController {
    @Autowired
    private AdopcionServicioImpl adopcionServicio;


    //adopcion donde pasas el usuario y publicacion
    @PostMapping("/solicitar")
    public ResponseEntity<AdopcionDTO> solicitarAdopcion(@RequestBody AdopcionSolicitudDTO solicitudDTO) {
        return ResponseEntity.ok(adopcionServicio.solicitarAdopcion(solicitudDTO.getUsuarioId(), solicitudDTO.getPublicacionId()));
    }

    //las solicitudes que hay en una publicación
    @GetMapping("/publicacion/{publicacionId}")
    public List<AdopcionDTO> obtenerSolicitudesPorPublicacion(@PathVariable Long publicacionId) {
        return adopcionServicio.obtenerSolicitudesPorPublicacion(publicacionId);
    }


    //adopcion aceptar la adopcion por id de la adopcion
    @PutMapping("/{adopcionId}/aceptar")
    public ResponseEntity<AdopcionDTO> aceptarSolicitud(@PathVariable Long adopcionId) {
        AdopcionDTO adopcionDTO = adopcionServicio.aceptarSolicitud(adopcionId);
        return new ResponseEntity<>(adopcionDTO, HttpStatus.OK);
    }


    //adopcion rechazar la adopcion por id de la adopcion
    @PutMapping("/{adopcionId}/rechazar")
    public ResponseEntity<AdopcionDTO> rechazarSolicitud(@PathVariable Long adopcionId) {
        AdopcionDTO adopcionDTO = adopcionServicio.rechazarSolicitud(adopcionId);
        return new ResponseEntity<>(adopcionDTO, HttpStatus.OK);
    }

    //solicitudes que mandó el usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AdopcionDTO>> obtenerSolicitudesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(adopcionServicio.obtenerSolicitudesPorUsuario(usuarioId));
    }

}
