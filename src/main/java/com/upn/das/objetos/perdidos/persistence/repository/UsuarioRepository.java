package com.upn.das.objetos.perdidos.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upn.das.objetos.perdidos.persistence.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByCorreoAndContrasenia(String correo, String contra);
}
