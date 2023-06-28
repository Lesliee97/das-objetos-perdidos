package com.upn.das.objetos.perdidos.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upn.das.objetos.perdidos.persistence.entity.ObjetoPerdido;
import com.upn.das.objetos.perdidos.persistence.repository.ObjetoPerdidoRepository;
import com.upn.das.objetos.perdidos.service.IObjetoPerdidoService;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoRequestDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoResponseDTO;

@Service
@Transactional
public class ObjetoPerdidoServiceImpl implements IObjetoPerdidoService {

	private ModelMapper mapper = new ModelMapper();
	private Logger log = LoggerFactory.getLogger(ObjetoPerdidoServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ObjetoPerdidoRepository objetoPerdidoRepository;

	@Override
	public ObjetoPerdidoResponseDTO findById(Long idObjeto) {
		Optional<ObjetoPerdido> encontrado = this.objetoPerdidoRepository.findById(idObjeto);

		if (encontrado.isPresent()) {
			ObjetoPerdido objeto = encontrado.get();
			ObjetoPerdidoResponseDTO response = this.mapper.map(objeto, ObjetoPerdidoResponseDTO.class);

			if (objeto.getEvidencia() != null) {
				response.setEvidenciaB64(Base64.getEncoder().encodeToString(objeto.getEvidencia()));
			}

			return response;
		}

		return null;
	}

	@Override
	public List<ObjetoPerdidoResponseDTO> getAllObjetosPerdidos() {
		log.info("Se listaran todos los objetos perdidos en estado pendiente y solicitado");
		ObjetoPerdidoResponseDTO objetoResponse;
		List<ObjetoPerdidoResponseDTO> response = new ArrayList<>();
		List<ObjetoPerdido> listaObjetos = this.objetoPerdidoRepository.findAll();

		if (!listaObjetos.isEmpty()) {
			for (ObjetoPerdido objetoPerdido : listaObjetos) {
				if (objetoPerdido.getEstado().equals('P') || objetoPerdido.getEstado().equals('S')) {
					objetoResponse = this.mapper.map(objetoPerdido, ObjetoPerdidoResponseDTO.class);
					if (objetoPerdido.getEvidencia() != null) {
						objetoResponse
								.setEvidenciaB64(Base64.getEncoder().encodeToString(objetoPerdido.getEvidencia()));
					}
					response.add(objetoResponse);
				}
			}
			return response;
		}

		return null;
	}

	@Override
	public ObjetoPerdidoResponseDTO saveObjetoPerdido(ObjetoPerdidoRequestDTO objeto) {
		try {
			ObjetoPerdido objetoPerdido = this.mapper.map(objeto, ObjetoPerdido.class);

			if (objeto.getEvidenciaB64() != null && !objeto.getEvidenciaB64().isEmpty()) {
				byte[] imageData = Base64.getDecoder().decode(objeto.getEvidenciaB64());
				objetoPerdido.setEvidencia(imageData);
			}

			objetoPerdido.setEstado('P');

			ObjetoPerdidoResponseDTO response = this.mapper.map(this.objetoPerdidoRepository.save(objetoPerdido),
					ObjetoPerdidoResponseDTO.class);
			log.info("Se guardó la entidad.");

			if (objeto.getEvidenciaB64() != null && !objeto.getEvidenciaB64().isEmpty()) {
				response.setEvidenciaB64(Base64.getEncoder().encodeToString(objetoPerdido.getEvidencia()));
			}

			envioCorreoNuevoObjeto(response);
			log.info("Se enviaron los correos.");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void envioCorreoNuevoObjeto(ObjetoPerdidoResponseDTO response) {
		try {

			List<String> correosUsuarios = Arrays.asList("pedrovergara26@gmail.com", "n00252467@upn.pe",
					"n00279437@upn.pe");

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			for (String correo : correosUsuarios) {
				helper.setTo(correo);
				helper.setSubject("¡Se registró un nuevo objeto perdido! Verifica si es tuyo.");
				helper.setText("<h2>Objeto perdido encontrado en: " + response.getLugarEncontrado() + ". </h2>"
						+ "El día de hoy se registro un objeto perdido, encontrado el día: "
						+ response.getFechaEncontrado().toString() + ".<br>"
						+ "La descripción brindada del objeto encontrado es: " + response.getDescripcion()
						+ "<br>Se adjunta la imagen de referencia otorgada por la persona que encontró el objeto:<br>"
						+ "<img src='data:image/jpg;base64," + response.getEvidenciaB64() + "'><br>"
						+ "<br>Gracias por su atención.<br><br>"
						+ "<b>Este es un correo automático, por favor no responder.</b>", true);

				mailSender.send(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
