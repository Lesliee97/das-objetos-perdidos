package com.upn.das.objetos.perdidos.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * @author Huawei
 */
@Data
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_USUARIO")
	private Long idUsuario;
	@Basic(optional = false)
	@Column(name = "DNI")
	private String dni;
	@Basic(optional = false)
	@Column(name = "NOMBRES")
	private String nombres;
	@Basic(optional = false)
	@Column(name = "APELLIDOS")
	private String apellidos;
	@Basic(optional = false)
	@Column(name = "CORREO")
	private String correo;
	@Basic(optional = false)
	@Column(name = "CONTRASENIA")
	private String contrasenia;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<SolicitudObjeto> solicitudObjetoList;
	@JoinColumn(name = "ID_ROL_USUARIO", referencedColumnName = "ID_ROL_USUARIO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private RolUsuario rolUsuario;

}
