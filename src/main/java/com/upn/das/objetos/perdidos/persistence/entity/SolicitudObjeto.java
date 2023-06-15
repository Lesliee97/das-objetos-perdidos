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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "SOLICITUD_OBJETO")
public class SolicitudObjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_SOLICITUD")
	private Long idSolicitud;
	@Basic(optional = false)
	@Column(name = "FECHA_SOLICITUD")
	@Temporal(TemporalType.DATE)
	private Date fechaSolicitud;
	@Basic(optional = false)
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@Basic(optional = false)
	@Column(name = "LUGAR")
	private String lugar;
	@Basic(optional = false)
	@Column(name = "FECHA_EXTRAVIO")
	@Temporal(TemporalType.DATE)
	private Date fechaExtravio;
	@Basic(optional = false)
	@Lob
	@Column(name = "EVIDENCIA")
	private byte[] evidencia;
	@JoinColumn(name = "ID_OBJETO_PERDIDO", referencedColumnName = "ID_OBJETO_PERDIDO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ObjetoPerdido objetoPerdido;
	@JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Usuario usuario;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudObjeto", fetch = FetchType.LAZY)
	private List<ConstanciaDigital> constanciaDigitalList;
}
