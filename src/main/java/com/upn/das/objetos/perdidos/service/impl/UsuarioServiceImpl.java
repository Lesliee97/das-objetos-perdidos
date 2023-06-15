package com.upn.das.objetos.perdidos.service.impl;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upn.das.objetos.perdidos.persistence.entity.Usuario;
import com.upn.das.objetos.perdidos.persistence.repository.UsuarioRepository;
import com.upn.das.objetos.perdidos.service.IUsuarioService;
import com.upn.das.objetos.perdidos.service.dto.UsuarioDTO;

@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService {

	private ModelMapper mapper = new ModelMapper();
	private Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UsuarioDTO login(String correo, String pass) {
		this.mapper.getConfiguration().setAmbiguityIgnored(true);

		log.info("Inicio de login para el correo: " + correo);

		Usuario user = this.usuarioRepository.findByCorreoAndContrasenia(correo, pass);

		if (!Objects.isNull(user)) {
			UsuarioDTO response = this.mapper.map(user, UsuarioDTO.class);
			response.setRol(user.getRolUsuario().getNombreRol());
			return response;
		}

		return null;
	}

}
