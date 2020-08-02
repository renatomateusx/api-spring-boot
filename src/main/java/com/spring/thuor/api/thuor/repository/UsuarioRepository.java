package com.spring.thuor.api.thuor.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.thuor.api.thuor.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findById(long id);
	Usuario findByEmail(String email);
	
}
