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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * @author Huawei
 */
@Data
@Entity
@Table(name = "ROL_USUARIO")
public class RolUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_ROL_USUARIO")
	private Long idRolUsuario;
	@Basic(optional = false)
	@Column(name = "NOMBRE_ROL")
	private String nombreRol;
	@Column(name = "DESC_ROL")
	private String descRol;
	@Basic(optional = false)
	@Column(name = "ESTADO")
	private Character estado;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rolUsuario", fetch = FetchType.LAZY)
	private List<Usuario> usuarioList;

}
