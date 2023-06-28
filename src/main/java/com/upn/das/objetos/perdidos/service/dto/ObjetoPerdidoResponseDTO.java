package com.upn.das.objetos.perdidos.service.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ObjetoPerdidoResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idObjetoPerdido;
	private String dniEncontrado;
	private String personaEncontrado;
	private String telefonoEncontrado;
	private String lugarEncontrado;
	private Date fechaEncontrado;
	private String descripcion;
	private String evidenciaB64;
	private String estado;
}
