package com.upn.das.objetos.perdidos.service;

import com.upn.das.objetos.perdidos.service.dto.UsuarioDTO;

public interface IUsuarioService {

	UsuarioDTO login(String correo, String pass);
}
