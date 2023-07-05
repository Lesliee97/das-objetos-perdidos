package com.upn.das.objetos.perdidos.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upn.das.objetos.perdidos.service.IObjetoPerdidoService;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoRequestDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoResponseDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoUpdateDTO;
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

	@GetMapping(value = "/findById/{idObjeto}")
	@ApiOperation(value = "Obtiene la información de un objeto buscado por ID.")
	@ApiResponses({ @ApiResponse(code = 200, message = Constantes.HTTP_TEXT_200),
			@ApiResponse(code = 400, message = Constantes.HTTP_TEXT_400),
			@ApiResponse(code = 401, message = Constantes.HTTP_TEXT_401),
			@ApiResponse(code = 403, message = Constantes.HTTP_TEXT_403),
			@ApiResponse(code = 500, message = Constantes.HTTP_TEXT_500) })
	public ResponseEntity<Object> findById(@PathVariable Long idObjeto) {
		try {
			final ObjetoPerdidoResponseDTO response = this.objetoPerdidoService.findById(idObjeto);
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

	@GetMapping(value = "/getAllObjetosPerdidos")
	@ApiOperation(value = "Obtiene los objetos perdidos pendientes y solicitados.")
	@ApiResponses({ @ApiResponse(code = 200, message = Constantes.HTTP_TEXT_200),
			@ApiResponse(code = 400, message = Constantes.HTTP_TEXT_400),
			@ApiResponse(code = 401, message = Constantes.HTTP_TEXT_401),
			@ApiResponse(code = 403, message = Constantes.HTTP_TEXT_403),
			@ApiResponse(code = 500, message = Constantes.HTTP_TEXT_500) })
	public ResponseEntity<Object> getAllObjetosPerdidos() {
		try {
			final List<ObjetoPerdidoResponseDTO> response = this.objetoPerdidoService.getAllObjetosPerdidos();
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

	@PostMapping(value = "/saveObjetoPerdido")
	@ApiOperation(value = "Guarda un nuevo objeto perdido.")
	@ApiResponses({ @ApiResponse(code = 201, message = Constantes.HTTP_TEXT_201),
			@ApiResponse(code = 400, message = Constantes.HTTP_TEXT_400),
			@ApiResponse(code = 401, message = Constantes.HTTP_TEXT_401),
			@ApiResponse(code = 403, message = Constantes.HTTP_TEXT_403),
			@ApiResponse(code = 500, message = Constantes.HTTP_TEXT_500) })
	public ResponseEntity<Object> saveObjetoPerdido(@RequestBody ObjetoPerdidoRequestDTO objetoPerdidoDTO) {
		try {
			final ObjetoPerdidoResponseDTO response = this.objetoPerdidoService.saveObjetoPerdido(objetoPerdidoDTO);
			if (Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
						.body("No se encontraron datos");
			} else {
				return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());
		}

	}

	@PutMapping(value = "/updateObjetoPerdido")
	@ApiOperation(value = "Actualiza un objeto perdido.")
	@ApiResponses({ @ApiResponse(code = 201, message = Constantes.HTTP_TEXT_201),
			@ApiResponse(code = 400, message = Constantes.HTTP_TEXT_400),
			@ApiResponse(code = 401, message = Constantes.HTTP_TEXT_401),
			@ApiResponse(code = 403, message = Constantes.HTTP_TEXT_403),
			@ApiResponse(code = 500, message = Constantes.HTTP_TEXT_500) })
	public ResponseEntity<Object> updateObjetoPerdido(@RequestBody ObjetoPerdidoUpdateDTO objetoPerdidoDTO) {
		try {
			final ObjetoPerdidoResponseDTO response = this.objetoPerdidoService.updateObjetoPerdido(objetoPerdidoDTO);
			if (Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
						.body("No se encontraron datos");
			} else {
				return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());
		}

	}
}
