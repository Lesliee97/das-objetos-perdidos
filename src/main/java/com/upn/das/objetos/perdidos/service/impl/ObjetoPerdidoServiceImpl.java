package com.upn.das.objetos.perdidos.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
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

import com.upn.das.objetos.perdidos.persistence.entity.AlertaObjeto;
import com.upn.das.objetos.perdidos.persistence.entity.ObjetoPerdido;
import com.upn.das.objetos.perdidos.persistence.repository.AlertaObjetoRepository;
import com.upn.das.objetos.perdidos.persistence.repository.ObjetoPerdidoRepository;
import com.upn.das.objetos.perdidos.service.IObjetoPerdidoService;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoRequestDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoResponseDTO;
import com.upn.das.objetos.perdidos.service.dto.ObjetoPerdidoUpdateDTO;

@Service
@Transactional
public class ObjetoPerdidoServiceImpl implements IObjetoPerdidoService {

	private ModelMapper mapper = new ModelMapper();
	private Logger log = LoggerFactory.getLogger(ObjetoPerdidoServiceImpl.class);

	ZoneId zonaHoraria = ZoneId.of("America/Lima");
	LocalDateTime localDateTime = LocalDateTime.now(zonaHoraria);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ObjetoPerdidoRepository objetoPerdidoRepository;

	@Autowired
	private AlertaObjetoRepository alertaObjetoRepository;

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

			objetoPerdido = this.objetoPerdidoRepository.save(objetoPerdido);

			ObjetoPerdidoResponseDTO response = this.mapper.map(objetoPerdido, ObjetoPerdidoResponseDTO.class);
			log.info("Se guardó la entidad.");

			if (objeto.getEvidenciaB64() != null && !objeto.getEvidenciaB64().isEmpty()) {
				response.setEvidenciaB64(Base64.getEncoder().encodeToString(objetoPerdido.getEvidencia()));
			}

			envioCorreoNuevoObjeto(response);
			log.info("Se enviaron los correos.");

			Date fechaActual = Date.from(localDateTime.atZone(zonaHoraria).toInstant());

			AlertaObjeto alerta = new AlertaObjeto();
			alerta.setObjetoPerdido(objetoPerdido);
			alerta.setEstado('E');
			alerta.setFechaAlerta(fechaActual);

			alerta = this.alertaObjetoRepository.save(alerta);

			log.info("Se guardo el envío de la alerta digital.");

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void envioCorreoNuevoObjeto(ObjetoPerdidoResponseDTO response) {
		try {

			List<String> correosUsuarios = Arrays.asList("n00252467@upn.pe", "n00264979@upn.pe", "n00244204@upn.pe",
					"n00265438@upn.pe", "n00245418@upn.pe", "n00238776@upn.pe", "n00279437@upn.pe");

			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject("¡Se registró un nuevo objeto perdido! Verifica si es tuyo.");
			helper.setText("<h2>Objeto perdido: <b>" + response.getNombre() + "</b>, encontrado en: <b>"
					+ response.getLugarEncontrado() + "</b>. </h2>"
					+ "<b>El día de hoy se registro un objeto perdido, encontrado el día:</b> "
					+ formato.format(response.getFechaEncontrado()) + ".<br>"
					+ "<b>La descripción brindada del objeto encontrado es:</b> " + response.getDescripcion()
					+ "<br><b>Se adjunta la imagen de referencia otorgada por la persona que encontró el objeto:</b><br>"
					+ "<img src='data:image/jpg;base64," + response.getEvidenciaB64() + "'><br>"
					+ "<br>Gracias por su atención.<br><br>"
					+ "<b>Este es un correo automático, por favor no responder.</b>", true);

			for (String correo : correosUsuarios) {
				helper.setTo(correo);
				mailSender.send(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ObjetoPerdidoResponseDTO updateObjetoPerdido(ObjetoPerdidoUpdateDTO objeto) {
		Optional<ObjetoPerdido> buscado = this.objetoPerdidoRepository.findById(objeto.getIdObjetoPerdido());
		if (buscado.isPresent()) {
			ObjetoPerdido objetoModel = buscado.get();
			objetoModel.setDescripcion(objeto.getDescripcion());
			objetoModel.setNombre(objeto.getNombre());

			objetoModel = this.objetoPerdidoRepository.save(objetoModel);

			ObjetoPerdidoResponseDTO response = this.mapper.map(objetoModel, ObjetoPerdidoResponseDTO.class);
			return response;
		}
		return null;
	}
}
