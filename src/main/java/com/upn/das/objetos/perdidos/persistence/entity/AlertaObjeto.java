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
@Table(name = "ALERTA_OBJETO")
public class AlertaObjeto implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_ALERTA")
	private Long idAlerta;
	@Basic(optional = false)
	@Column(name = "FECHA_ALERTA")
	@Temporal(TemporalType.DATE)
	private Date fechaAlerta;
	@Basic(optional = false)
	@Column(name = "ESTADO")
	private Character estado;
	@JoinColumn(name = "ID_OBJETO_PERDIDO", referencedColumnName = "ID_OBJETO_PERDIDO")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ObjetoPerdido objetoPerdido;
}
