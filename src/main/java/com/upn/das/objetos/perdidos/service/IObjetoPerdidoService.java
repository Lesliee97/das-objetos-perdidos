package com.upn.das.objetos.perdidos.service;

import java.util.List;

import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoRequestDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoResponseDTO;

public interface IObjetoPerdidoService {

	List<ObjetoPerdidoResponseDTO> getAllObjetosPerdidos();

	ObjetoPerdidoResponseDTO saveObjetoPerdido(ObjetoPerdidoRequestDTO objeto);

	ObjetoPerdidoResponseDTO findById(Long idObjeto);
}
