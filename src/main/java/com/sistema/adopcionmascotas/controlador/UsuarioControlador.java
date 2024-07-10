package com.sistema.adopcionmascotas.controlador;

import com.sistema.adopcionmascotas.dto.*;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {
    @Value("${file.uploaduser-dir}")
    private String uploadDir;

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
                                                              @RequestParam("nombre") String nombre,
                                                              @RequestParam("apellido") String apellido,
                                                              @RequestParam("dni") String dni,
                                                              @RequestParam("edad") int edad,
                                                              @RequestParam("sexo") String sexo,
                                                              @RequestParam("sobremi") String sobremi,
                                                              @RequestParam("telefono")int telefono,
                                                              @RequestParam(value = "foto", required = false) MultipartFile foto) {
        AjusteUsuarioDTO ajusteUsuarioDTO1 = new AjusteUsuarioDTO();
        ajusteUsuarioDTO1.setNombre(nombre);
        ajusteUsuarioDTO1.setApellidos(apellido);
        ajusteUsuarioDTO1.setDni(dni);
        ajusteUsuarioDTO1.setEdad(edad);
        ajusteUsuarioDTO1.setSexo(sexo);
        ajusteUsuarioDTO1.setSobremi(sobremi);
        ajusteUsuarioDTO1.setTelefono(telefono);

        if (foto != null && !foto.isEmpty()) {
            try {
                // Obtener la información del usuario actual
                AjusteUsuarioDTO usuarioActual = usuarioServicio.obtenerUsuarioPorId(id);

                // Si el usuario ya tiene una imagen de perfil, eliminarla
                if (usuarioActual.getImagenPerfilPath() != null && !usuarioActual.getImagenPerfilPath().isEmpty()) {
                    Path rutaImagenAnterior = Paths.get(uploadDir).resolve(usuarioActual.getImagenPerfilPath()).toAbsolutePath();
                    Files.deleteIfExists(rutaImagenAnterior);
                }

                // Generar un nombre único para la nueva imagen
                String fileName = generateUniqueFileName(foto.getOriginalFilename());
                Path path = Paths.get(uploadDir + fileName);
                Files.write(path, foto.getBytes());
                ajusteUsuarioDTO1.setImagenPerfilPath(fileName);
            } catch (IOException e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        AjusteUsuarioDTO usuarioActualizado = usuarioServicio.actualizarUsuario(id, ajusteUsuarioDTO1);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
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

    // Método para servir imágenes estáticas
    @GetMapping(value = "/images/{nombreImagen:.+}")
    public ResponseEntity<?> findImageFromPath(@PathVariable("nombreImagen") String nombreImagen) throws IOException {
        Path rutaArchivo = Paths.get(uploadDir).resolve(nombreImagen).toAbsolutePath();

        Resource resource = new UrlResource(rutaArchivo.toUri());

        if (resource.exists() && resource.isReadable()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreImagen + "\"");
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Imagen no encontrada");
        }
    }


    private String generateUniqueFileName(String originalFileName) {
        String fileName = originalFileName;
        String extension = "";
        int dotIndex = originalFileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileName = originalFileName.substring(0, dotIndex);
            extension = originalFileName.substring(dotIndex);
        }

        int counter = 1;
        Path path = Paths.get(uploadDir + originalFileName);
        while (Files.exists(path)) {
            fileName = originalFileName.substring(0, dotIndex) + "_" + counter + extension;
            path = Paths.get(uploadDir + fileName);
            counter++;
        }
        return fileName;
    }
}
