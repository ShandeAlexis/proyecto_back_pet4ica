package com.sistema.adopcionmascotas.controlador;

import com.sistema.adopcionmascotas.dto.FavoritoDTO;
import com.sistema.adopcionmascotas.servicio.FavoritoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {
    @Autowired
    private FavoritoServicio favoritoServicio;

    @PostMapping("/agregar")
    public ResponseEntity<FavoritoDTO> agregarFavorito(@RequestParam Long usuarioId, @RequestParam Long publicacionId) {
        return ResponseEntity.ok(favoritoServicio.agregarFavorito(usuarioId, publicacionId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoDTO>> obtenerFavoritosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoritoServicio.obtenerFavoritosPorUsuario(usuarioId));
    }

    @DeleteMapping("/eliminar/{favoritoId}")
    public ResponseEntity<Void> eliminarFavorito(@PathVariable Long favoritoId) {
        favoritoServicio.eliminarFavorito(favoritoId);
        return ResponseEntity.noContent().build();
    }
}
