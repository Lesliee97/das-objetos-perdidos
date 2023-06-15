package com.upn.das.objetos.perdidos.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 *
 * @author Huawei
 */
@Data
@Entity
@Table(name = "OBJETO_PERDIDO")
public class ObjetoPerdido implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_OBJETO_PERDIDO")
	private Long idObjetoPerdido;
	@Basic(optional = false)
	@Column(name = "DNI_ENCONTRADO")
	private String dniEncontrado;
	@Basic(optional = false)
	@Column(name = "PERSONA_ENCONTRADO")
	private String personaEncontrado;
	@Basic(optional = false)
	@Column(name = "TELEFONO_ENCONTRADO")
	private String telefonoEncontrado;
	@Basic(optional = false)
	@Column(name = "LUGAR_ENCONTRADO")
	private String lugarEncontrado;
	@Basic(optional = false)
	@Column(name = "FECHA_ENCONTRADO")
	@Temporal(TemporalType.DATE)
	private Date fechaEncontrado;
	@Basic(optional = false)
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Basic(optional = false)
	@Lob
	@Column(name = "EVIDENCIA")
	private byte[] evidencia;
	@Basic(optional = false)
	@Column(name = "ESTADO")
	private Character estado;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "objetoPerdido", fetch = FetchType.LAZY)
	private List<AlertaObjeto> alertaObjetoList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "objetoPerdido", fetch = FetchType.LAZY)
	private List<SolicitudObjeto> solicitudObjetoList;
}
