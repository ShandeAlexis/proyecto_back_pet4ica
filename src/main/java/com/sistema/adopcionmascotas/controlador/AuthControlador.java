package com.sistema.adopcionmascotas.controlador;

import java.util.Collections;

import com.sistema.adopcionmascotas.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.adopcionmascotas.dto.LoginDTO;
import com.sistema.adopcionmascotas.dto.RegistroDTO;
import com.sistema.adopcionmascotas.entidades.Rol;
import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.repositorio.RolRepositorio;
import com.sistema.adopcionmascotas.repositorio.UsuarioRepositorio;
import com.sistema.adopcionmascotas.seguridad.JWTAuthResonseDTO;
import com.sistema.adopcionmascotas.seguridad.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/iniciarSesion")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		//obtenemos el token del jwtTokenProvider
		String token = jwtTokenProvider.generarToken(authentication);

		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
		try {
			usuarioServicio.registrarUsuario(registroDTO);
			return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al registrar usuario", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
