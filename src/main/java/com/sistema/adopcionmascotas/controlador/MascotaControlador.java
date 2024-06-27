package com.sistema.adopcionmascotas.controlador;

import com.sistema.adopcionmascotas.dto.MascotaDTO;
import com.sistema.adopcionmascotas.servicio.MascotaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@CrossOrigin(origins = "http://localhost:5173") // Permitir solicitudes desde localhost:5173
public class MascotaControlador {
    private static final String UPLOAD_DIR = "fotos/";

    @Autowired
    private MascotaServicio mascotaServicio;

    @PostMapping
    public ResponseEntity<MascotaDTO> crearMascota(@Valid @RequestParam("nombre") String nombre,
                                                   @RequestParam("raza") String raza,
                                                   @RequestParam("edad") int edad,
                                                   @RequestParam("foto") MultipartFile foto) {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setNombre(nombre);
        mascotaDTO.setRaza(raza);
        mascotaDTO.setEdad(edad);
        if (!foto.isEmpty()) {
            try {
                // Generar un nombre único para la imagen
                String fileName = generateUniqueFileName(foto.getOriginalFilename());
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, foto.getBytes());
                mascotaDTO.setImagenPath(fileName);
            } catch (IOException e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        MascotaDTO nuevaMascota = mascotaServicio.crearMascota(mascotaDTO);
        return new ResponseEntity<>(nuevaMascota, HttpStatus.CREATED);
    }

    @GetMapping
    public List<MascotaDTO> listarMascotas() {
        return mascotaServicio.obtenerTodasLasMascotas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerMascotaPorId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(mascotaServicio.obtenerMascotaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> actualizarMascota( @Valid @PathVariable(name = "id") Long id,
                                                        @RequestParam("nombre") String nombre,
                                                        @RequestParam("raza") String raza,
                                                        @RequestParam("edad") int edad,
                                                        @RequestParam(value = "foto", required = false) MultipartFile foto) {
        MascotaDTO mascotaDTO = new MascotaDTO();
        mascotaDTO.setNombre(nombre);
        mascotaDTO.setRaza(raza);
        mascotaDTO.setEdad(edad);
        if (foto != null && !foto.isEmpty()) {
            try {
                // Guardar la nueva foto en el sistema de archivos
                String fileName = foto.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.write(path, foto.getBytes());
                mascotaDTO.setImagenPath(fileName);
            } catch (IOException e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        MascotaDTO mascotaActualizada = mascotaServicio.actualizarMascota(id, mascotaDTO);
        return new ResponseEntity<>(mascotaActualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarMascota(@PathVariable(name = "id") Long id) {
        mascotaServicio.eliminarMascota(id);
        return new ResponseEntity<>("Mascota eliminada con éxito", HttpStatus.OK);
    }

    // Método para servir imágenes estáticas
    @GetMapping(value = "/images/{nombreImagen:.+}")
    public ResponseEntity<?> findImageFromPath(@PathVariable("nombreImagen") String nombreImagen) throws IOException {
        Path rutaArchivo = Paths.get("C:/Users/Alexis/Downloads/mini-sistema-blog-api-rest-spring-master/sistema-blog-spring-boot-api-rest/fotos").resolve(nombreImagen).toAbsolutePath();

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
        Path path = Paths.get(UPLOAD_DIR + originalFileName);
        while (Files.exists(path)) {
            fileName = originalFileName.substring(0, dotIndex) + "_" + counter + extension;
            path = Paths.get(UPLOAD_DIR + fileName);
            counter++;
        }
        return fileName;
    }
}
