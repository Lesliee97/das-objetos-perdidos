package com.upn.das.objetos.perdidos.service.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	private String dni;
	private String nombres;
	private String apellidos;
	private String correo;
	private String rol;
}
