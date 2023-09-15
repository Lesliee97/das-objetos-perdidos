package com.upn.das.objetos.perdidos.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upn.das.objetos.perdidos.service.IUsuarioService;
import com.upn.das.objetos.perdidos.service.dto.UsuarioDTO;
import com.upn.das.objetos.perdidos.utils.Constantes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping("usuario")
@Api(tags = "UsuarioApi", value = "/usuario", description = "API para el ingreso de Usuario.")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	// Nueva version
	@PostMapping(value = "/login")
	@ApiOperation(value = Constantes.LOGIN)
	@ApiResponses({ @ApiResponse(code = 201, message = Constantes.HTTP_TEXT_201),
			@ApiResponse(code = 400, message = Constantes.HTTP_TEXT_400),
			@ApiResponse(code = 401, message = Constantes.HTTP_TEXT_401),
			@ApiResponse(code = 403, message = Constantes.HTTP_TEXT_403),
			@ApiResponse(code = 500, message = Constantes.HTTP_TEXT_500) })
	public ResponseEntity<Object> login(@RequestParam String correo, @RequestParam String contra) {
		try {
			final UsuarioDTO response = this.usuarioService.login(correo, contra);
			if (Objects.isNull(response)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
						.body("No se encontró usuario con los datos ingresados en los parametros");
			} else {
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(e.getLocalizedMessage());
		}

	}
}
