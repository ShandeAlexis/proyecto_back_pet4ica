package com.sistema.adopcionmascotas.seguridad;

import java.util.*;

import com.sistema.adopcionmascotas.entidades.Usuario;
import com.sistema.adopcionmascotas.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sistema.adopcionmascotas.excepciones.AppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;



	public String generarToken(Authentication authentication) {
		String email = authentication.getName();

		Date fechaActual = new Date(System.currentTimeMillis());
		Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);

		Map<String , Object> headers= new HashMap<>();

		String token = Jwts.builder()
				.setClaims(extraClaims(authentication))
				.setSubject(email)
				.setIssuedAt(fechaActual)
				.setExpiration(fechaExpiracion)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();

		return token;
	}

	private Map<String, Object> extraClaims(Authentication authentication){
		User userPrincipal = (User) authentication.getPrincipal();
		Usuario usuario = usuarioRepositorio.findByEmail(userPrincipal.getUsername())
				.orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ese email: " + userPrincipal.getUsername()));
		Map<String,Object> extraClaims= new HashMap<>();
		extraClaims.put("permisos",authentication.getAuthorities());
		extraClaims.put("id",usuario.getId());
		extraClaims.put("nombre",usuario.getNombre());
		return extraClaims;
	}

	public Long obtenerIdDelJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.get("id").toString());
	}
	private byte[] generateKey() {
		return Base64.getEncoder().encode(jwtSecret.getBytes());
	}


	public String obtenerUsernameDelJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		}catch (SignatureException ex) {
			throw new AppException(HttpStatus.BAD_REQUEST,"Firma JWT no valida");
		}
		catch (MalformedJwtException ex) {
			throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT no valida");
		}
		catch (ExpiredJwtException ex) {
			throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT caducado");
		}
		catch (UnsupportedJwtException ex) {
			throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible");
		}
		catch (IllegalArgumentException ex) {
			throw new AppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT esta vacia");
		}
	}
}

