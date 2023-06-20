package com.upn.das.objetos.perdidos.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upn.das.objetos.perdidos.service.IObjetoPerdidoService;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoDTO;
import com.upn.das.objetos.perdidos.utils.Constantes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("objetoPerdido")
@Api(tags = "ObjetoPerdidoApi", value = "/objetoPerdido", description = "API para el mantenimiento de objetos.")
public class ObjetoPerdidoController {

	@Autowired
	IObjetoPerdidoService objetoPerdidoService;

	@GetMapping(value = "/getAllObjetosPerdidos")
	@ApiOperation(value = "Obtiene los objetos perdidos pendientes y solicitados.")
	@ApiResponses({ @ApiResponse(code = 200, message = Constantes.HTTP_TEXT_200),
			@ApiResponse(code = 400, message = Constantes.HTTP_TEXT_400),
			@ApiResponse(code = 401, message = Constantes.HTTP_TEXT_401),
			@ApiResponse(code = 403, message = Constantes.HTTP_TEXT_403),
			@ApiResponse(code = 500, message = Constantes.HTTP_TEXT_500) })
	public ResponseEntity<Object> getAllObjetosPerdidos() {
		try {
			final List<ObjetoPerdidoDTO> response = this.objetoPerdidoService.getAllObjetosPerdidos();
			if (Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
						.body("No se encontraron datos");
			} else {
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());
		}

	}
}
