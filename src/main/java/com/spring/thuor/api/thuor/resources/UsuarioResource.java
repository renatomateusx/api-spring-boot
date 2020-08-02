package com.spring.thuor.api.thuor.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.thuor.api.thuor.models.Usuario;
import com.spring.thuor.api.thuor.repository.UsuarioRepository;
import com.spring.thuor.api.thuor.rules.UsuarioRules;

@RestController
@RequestMapping(value="/api")
public class UsuarioResource  {

	/*@Autowired
	private JwtTokenUtil jwtTokenUtil;*/
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioRules usuarioRule;
	
	@GetMapping("/usuarios")
	public List<Usuario> listaUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@GetMapping("/usuario/{id}")
	public Usuario getSpecificUser(@PathVariable(value="id") long id) {
		return usuarioRepository.findById(id);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<Usuario> postUsuario(@RequestBody Usuario usuario) {
		return usuarioRule.checkRules(usuario);		
	}
	@DeleteMapping("/usuario")
	public void deleteUsuario(@RequestBody Usuario usuario) {
		usuarioRepository.delete(usuario);
	}
	@PutMapping("/usuario")
	public Usuario putUsuario(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	@PostMapping("/usuario/doLogin")
	public ResponseEntity<Usuario> doLogin(@RequestBody String email, String pass) {		
		return usuarioRule.doLogin(email, pass);
	}
}
