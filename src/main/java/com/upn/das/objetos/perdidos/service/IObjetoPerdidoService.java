package com.upn.das.objetos.perdidos.service;

import java.util.List;

import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoRequestDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoResponseDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoUpdateDTO;

public interface IObjetoPerdidoService {

	List<ObjetoPerdidoResponseDTO> getAllObjetosPerdidos();

	ObjetoPerdidoResponseDTO findById(Long idObjeto);

	ObjetoPerdidoResponseDTO saveObjetoPerdido(ObjetoPerdidoRequestDTO objeto);

	ObjetoPerdidoResponseDTO updateObjetoPerdido(ObjetoPerdidoUpdateDTO objeto);
}
