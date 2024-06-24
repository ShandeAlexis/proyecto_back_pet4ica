package com.sistema.adopcionmascotas.controlador;

import com.sistema.adopcionmascotas.dto.AdopcionDTO;
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


    @PostMapping
    public AdopcionDTO solicitarAdopcion(@RequestParam Long usuarioId, @RequestParam Long publicacionId) {
        return adopcionServicio.solicitarAdopcion(usuarioId, publicacionId);
    }

    @GetMapping("/publicacion/{publicacionId}")
    public List<AdopcionDTO> obtenerSolicitudesPorPublicacion(@PathVariable Long publicacionId) {
        return adopcionServicio.obtenerSolicitudesPorPublicacion(publicacionId);
    }



    @PutMapping("/{adopcionId}/aceptar")
    public ResponseEntity<AdopcionDTO> aceptarSolicitud(@PathVariable Long adopcionId) {
        AdopcionDTO adopcionDTO = adopcionServicio.aceptarSolicitud(adopcionId);
        return new ResponseEntity<>(adopcionDTO, HttpStatus.OK);
    }

    @PutMapping("/{adopcionId}/rechazar")
    public ResponseEntity<AdopcionDTO> rechazarSolicitud(@PathVariable Long adopcionId) {
        AdopcionDTO adopcionDTO = adopcionServicio.rechazarSolicitud(adopcionId);
        return new ResponseEntity<>(adopcionDTO, HttpStatus.OK);
    }

}
