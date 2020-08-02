package com.spring.thuor.api.thuor.rules;

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
	
	public ResponseEntity<Usuario> doLogin(String email, String pass) {
		Usuario usr = usuarioRepository.findByEmail(email);		
		if(usr != null){
			if(usr.getEmail() == pass) {
				
			}
			else {
				return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);
			}
		}
		else {
			return new ResponseEntity<Usuario>(HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<Usuario>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	
}
