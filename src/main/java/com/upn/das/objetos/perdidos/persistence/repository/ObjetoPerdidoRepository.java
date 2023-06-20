package com.upn.das.objetos.perdidos.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upn.das.objetos.perdidos.persistence.entity.ObjetoPerdido;

public interface ObjetoPerdidoRepository extends JpaRepository<ObjetoPerdido, Long> {

}
