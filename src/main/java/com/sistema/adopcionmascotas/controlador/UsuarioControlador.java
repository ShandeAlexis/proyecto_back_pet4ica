package com.sistema.adopcionmascotas.controlador;

import com.sistema.adopcionmascotas.dto.*;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroDTO registroDTO) {
        try {
            usuarioServicio.registrarUsuario(registroDTO);
            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public UsuarioRespuesta listarUsuarios(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String ordenarPor,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return usuarioServicio.obtenerTodosLosUsuarios(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AjusteUsuarioDTO> obtenerUsuarioPorId(@PathVariable(name = "id") long id) {
        AjusteUsuarioDTO usuario = usuarioServicio.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AjusteUsuarioDTO> actualizarUsuario(@Valid @PathVariable(name = "id") long id,
                                                              @RequestParam("nombre")String nombre,
                                                              @RequestParam("apellido")String apellido,
                                                              @RequestParam("dni")String dni,
                                                              @RequestParam("edad")int edad,
                                                              @RequestParam("sexo")String sexo,
                                                              @RequestParam("sobremi")String sobremi

    ) {
        AjusteUsuarioDTO ajusteUsuarioDTO1= new AjusteUsuarioDTO();
        ajusteUsuarioDTO1.setNombre(nombre);
        ajusteUsuarioDTO1.setApellidos(apellido);
        ajusteUsuarioDTO1.setDni(dni);
        ajusteUsuarioDTO1.setEdad(edad);
        ajusteUsuarioDTO1.setSexo(sexo);
        ajusteUsuarioDTO1.setSobremi(sobremi);

        AjusteUsuarioDTO usuarioactualizado = usuarioServicio.actualizarUsuario(id,ajusteUsuarioDTO1);
        return new ResponseEntity<>(usuarioactualizado,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable long id) {
        usuarioServicio.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
    }

    @GetMapping("/{id}/publicaciones")
    public List<PublicacionDTO> obtenerPublicacionesDeUsuarioPorId(@PathVariable Long id) {
        return usuarioServicio.obtenerPublicacionesDeUsuarioPorId(id);
    }
}
