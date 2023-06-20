package com.upn.das.objetos.perdidos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upn.das.objetos.perdidos.persistence.entity.ObjetoPerdido;
import com.upn.das.objetos.perdidos.persistence.repository.ObjetoPerdidoRepository;
import com.upn.das.objetos.perdidos.service.IObjetoPerdidoService;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoDTO;

@Service
@Transactional
public class ObjetoPerdidoServiceImpl implements IObjetoPerdidoService {

	private ModelMapper mapper = new ModelMapper();
	private Logger log = LoggerFactory.getLogger(ObjetoPerdidoServiceImpl.class);

	@Autowired
	private ObjetoPerdidoRepository objetoPerdidoRepository;

	@Override
	public List<ObjetoPerdidoDTO> getAllObjetosPerdidos() {
		log.info("Se listaran todos los objetos perdidos en estado pendiente y solicitado");
		ObjetoPerdidoDTO objetoResponse;
		List<ObjetoPerdidoDTO> response = new ArrayList<>();
		List<ObjetoPerdido> listaObjetos = this.objetoPerdidoRepository.findAll();

		if (!listaObjetos.isEmpty()) {
			for (ObjetoPerdido objetoPerdido : listaObjetos) {
				if (objetoPerdido.getEstado().equals('P') || objetoPerdido.getEstado().equals('S')) {
					objetoResponse = this.mapper.map(objetoPerdido, ObjetoPerdidoDTO.class);
					response.add(objetoResponse);
				}
			}
			return response;
		}

		return null;
	}

}
