package com.spring.thuor.api.thuor.rules;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.thuor.api.thuor.models.Usuario;
import com.spring.thuor.api.thuor.repository.UsuarioRepository;
import com.spring.thuor.api.thuor.token.UsuarioToken;

@Service
public class UsuarioRules {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioToken usuarioToken;
	
	public ResponseEntity<Usuario> checkRules(Usuario usuario) {		
		Usuario usr = usuarioRepository.findByEmail(usuario.getEmail());
		if(usr != null) return new ResponseEntity<Usuario>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
		Usuario usrInserted = usuarioRepository.save(usuario);
		if(usrInserted != null) return new ResponseEntity<Usuario>(HttpStatus.ACCEPTED);
		
		return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<Usuario> doLogin(Usuario usuario) {
		try {
			Usuario usr = usuarioRepository.findByEmail(usuario.getEmail());
			if(usr != null){
				if(usr.getPass().equals(usuario.getPass())) {
					String _token = usuarioToken.generateToken(usr);
					usr.setToken(_token);
					return new ResponseEntity<Usuario>(usr, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);
				}
			}
			else {
				return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);
			}
		}
		catch (Exception e) {
			throw e;
		}
	}
	
}
