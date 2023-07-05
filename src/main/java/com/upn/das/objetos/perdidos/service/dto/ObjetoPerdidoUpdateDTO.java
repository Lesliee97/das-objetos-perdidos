package com.upn.das.objetos.perdidos.service.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ObjetoPerdidoUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idObjetoPerdido;
	private String descripcion;
	private String nombre;
}
