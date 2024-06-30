package com.sistema.adopcionmascotas.servicio;

import com.sistema.adopcionmascotas.dto.*;
import com.sistema.adopcionmascotas.entidades.Usuario;

import java.util.List;

public interface UsuarioServicio {
    void registrarUsuario(RegistroDTO registroDTO);
    AjusteUsuarioDTO obtenerUsuarioPorId(long id);
    AjusteUsuarioDTO actualizarUsuario(long id, AjusteUsuarioDTO ajusteUsuarioDTO);
    UsuarioRespuesta obtenerTodosLosUsuarios(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
    List<PublicacionDTO> obtenerPublicacionesDeUsuarioPorId(Long usuarioId);

    void eliminarUsuario(long id);




}
