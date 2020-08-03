package com.spring.thuor.api.thuor.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.spring.thuor.api.thuor.models.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class UsuarioToken {
	@Value("${jwt.secret}")
	private String SECRET;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}

		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
		
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		}

		
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}

		
		public String generateToken(Usuario usuario) {
			Map<String, Object> claims = new HashMap<>();
			return doGenerateToken(claims, usuario.getNome());
		}

		
		private String doGenerateToken(Map<String, Object> claims, String subject) {
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
			.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		}

		
		public Boolean validateToken(String token, Usuario usuario) {
			final String username = getUsernameFromToken(token);
			return (username.equals(usuario.getNome()) && !isTokenExpired(token));
		}
	
	
}
