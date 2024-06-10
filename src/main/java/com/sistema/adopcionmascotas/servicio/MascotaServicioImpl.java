package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.MascotaDTO;
import com.sistema.adopcionmascotas.entidades.Mascota;
import com.sistema.adopcionmascotas.excepciones.ResourceNotFoundException;
import com.sistema.adopcionmascotas.repositorio.MascotaRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MascotaServicioImpl implements MascotaServicio{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Override
    public MascotaDTO crearMascota(MascotaDTO mascotaDTO) {
        Mascota mascota = modelMapper.map(mascotaDTO, Mascota.class);
        Mascota nuevaMascota = mascotaRepositorio.save(mascota);
        return modelMapper.map(nuevaMascota, MascotaDTO.class);
    }

    @Override
    public List<MascotaDTO> obtenerTodasLasMascotas() {
        List<Mascota> mascotas = mascotaRepositorio.findAll();
        return mascotas.stream().map(mascota -> modelMapper.map(mascota, MascotaDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MascotaDTO obtenerMascotaPorId(Long id) {
        Mascota mascota = mascotaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota", "id", id));
        return modelMapper.map(mascota, MascotaDTO.class);
    }

    @Override
    public MascotaDTO actualizarMascota(Long id, MascotaDTO mascotaDTO) {
        Mascota mascota = mascotaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota", "id", id));

        mascota.setNombre(mascotaDTO.getNombre());
        mascota.setRaza(mascotaDTO.getRaza());
        mascota.setEdad(mascotaDTO.getEdad());
        // Actualizar la ruta de la imagen
        if (mascotaDTO.getImagenPath() != null) {
            mascota.setImagenPath(mascotaDTO.getImagenPath());
        }

        Mascota mascotaActualizada = mascotaRepositorio.save(mascota);
        return modelMapper.map(mascotaActualizada, MascotaDTO.class);
    }

    @Override
    public void eliminarMascota(Long id) {
        Mascota mascota = mascotaRepositorio.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota", "id", id));
        mascotaRepositorio.delete(mascota);
    }
}
