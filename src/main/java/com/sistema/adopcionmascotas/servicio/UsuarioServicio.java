package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.PublicacionDTO;
import com.sistema.adopcionmascotas.dto.PublicacionRespuesta;
import com.sistema.adopcionmascotas.dto.RegistroDTO;
import com.sistema.adopcionmascotas.dto.UsuarioRespuesta;
import com.sistema.adopcionmascotas.entidades.Usuario;

import java.util.List;

public interface UsuarioServicio {
    void registrarUsuario(RegistroDTO registroDTO);
    Usuario obtenerUsuarioPorId(long id);
    Usuario actualizarUsuario(long id, RegistroDTO registroDTO);
    UsuarioRespuesta obtenerTodosLosUsuarios(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    List<PublicacionDTO> obtenerPublicacionesDeUsuarioPorId(Long usuarioId);

    void eliminarUsuario(long id);




}
