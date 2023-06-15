package com.upn.das.objetos.perdidos.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CONSTANCIA_DIGITAL")
public class ConstanciaDigital implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_CONSTANCIA")
	private Long idConstancia;
	@Basic(optional = false)
	@Column(name = "FECHA_CONSTANCIA")
	@Temporal(TemporalType.DATE)
	private Date fechaConstancia;
	@Column(name = "DESCRIPCION")
	private String descripcion;
	@JoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private SolicitudObjeto solicitudObjeto;
}
