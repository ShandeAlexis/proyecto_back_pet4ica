package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.*;
import com.sistema.adopcionmascotas.entidades.DetalleUsuario;
import com.sistema.adopcionmascotas.entidades.Publicacion;
import com.sistema.adopcionmascotas.entidades.Rol;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.excepciones.ResourceNotFoundException;
import com.sistema.adopcionmascotas.repositorio.RolRepositorio;
import com.sistema.adopcionmascotas.repositorio.UsuarioRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioRespuesta obtenerTodosLosUsuarios(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Usuario> usuarios = usuarioRepositorio.findAll(pageable);

        List<UsuarioDTO> contenido = usuarios.getContent().stream().map(this::mapearDTO)
                .collect(Collectors.toList());

        UsuarioRespuesta usuarioRespuesta = new UsuarioRespuesta();
        usuarioRespuesta.setContenido(contenido);
        usuarioRespuesta.setNumeroPagina(usuarios.getNumber());
        usuarioRespuesta.setMedidaPagina(usuarios.getSize());
        usuarioRespuesta.setTotalElementos(usuarios.getTotalElements());
        usuarioRespuesta.setTotalPaginas(usuarios.getTotalPages());
        usuarioRespuesta.setUltima(usuarios.isLast());

        return usuarioRespuesta;
    }

    @Override
    public List<PublicacionDTO> obtenerPublicacionesDeUsuarioPorId(Long usuarioId) {
        // Obtener el usuario por su ID
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        // Obtener las publicaciones del usuario y mapearlas a DTOs
        return usuario.getPublicaciones().stream()
                .map(publicacion -> modelMapper.map(publicacion, PublicacionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void registrarUsuario(RegistroDTO registroDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepositorio.findByNombre("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepositorio.save(usuario);
    }

    @Override
    public AjusteUsuarioDTO obtenerUsuarioPorId(long id) {
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","id",id));
        return mapearAjusteUsuarioDTO(usuario);
    }
    @Transactional
    public AjusteUsuarioDTO actualizarUsuario(long id, AjusteUsuarioDTO ajusteUsuarioDTO){
        Usuario usuario = usuarioRepositorio.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","id",id));
        // Obtener el detalle del usuario
        DetalleUsuario detalleUsuario = usuario.getDetalleUsuario();


        // Si el detalleUsuario es null, inicializar uno nuevo
        if (detalleUsuario == null) {
            detalleUsuario = new DetalleUsuario();
            usuario.setDetalleUsuario(detalleUsuario);
        }

        // Actualizar los campos del usuario
        usuario.setNombre(ajusteUsuarioDTO.getNombre());

        // Actualizar los campos del detalleUsuario
        detalleUsuario.setApellidos(ajusteUsuarioDTO.getApellidos());
        detalleUsuario.setSobremi(ajusteUsuarioDTO.getSobremi());
        detalleUsuario.setDni(ajusteUsuarioDTO.getDni());
        detalleUsuario.setEdad(ajusteUsuarioDTO.getEdad());
        detalleUsuario.setSexo(ajusteUsuarioDTO.getSexo());

        // Guardar y devolver el usuario actualizado
        Usuario usuarioactualizado= usuarioRepositorio.save(usuario);
        return mapearAjusteUsuarioDTO(usuarioactualizado);
    }





    @Override
    @Transactional
    public void eliminarUsuario(long id) {
        // Buscar el usuario por su ID
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.eliminarRoles(); // Eliminar los roles asociados al usuario


        try {
            // Eliminar el usuario
            usuarioRepositorio.delete(usuario);
        } catch (Exception e) {
            // Manejar la excepción
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Convierte entidad a DTO
    private UsuarioDTO mapearDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

        if (usuario.getDetalleUsuario() != null) {
            usuarioDTO.setDni(usuario.getDetalleUsuario().getDni());
            usuarioDTO.setApellidos(usuario.getDetalleUsuario().getApellidos());
            usuarioDTO.setEdad(usuario.getDetalleUsuario().getEdad());
            usuarioDTO.setSexo(usuario.getDetalleUsuario().getSexo());
        }

        return usuarioDTO;
    }
    private AjusteUsuarioDTO mapearAjusteUsuarioDTO(Usuario usuario) {
        AjusteUsuarioDTO ajusteUsuarioDTO = modelMapper.map(usuario, AjusteUsuarioDTO.class);
        if (usuario.getDetalleUsuario()!=null){
            ajusteUsuarioDTO.setApellidos(usuario.getDetalleUsuario().getApellidos());
            ajusteUsuarioDTO.setEdad(usuario.getDetalleUsuario().getEdad());
            ajusteUsuarioDTO.setDni(usuario.getDetalleUsuario().getDni());
            ajusteUsuarioDTO.setSobremi(usuario.getDetalleUsuario().getSobremi());
            ajusteUsuarioDTO.setSexo(usuario.getDetalleUsuario().getSexo());
        }
        return ajusteUsuarioDTO;
    }

    // Convierte de DTO a Entidad
    private Usuario mapearEntidad(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }



}
