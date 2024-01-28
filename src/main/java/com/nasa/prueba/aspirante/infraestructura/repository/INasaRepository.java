package com.nasa.prueba.aspirante.infraestructura.repository;

import com.nasa.prueba.aspirante.dominio.entities.NasaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface INasaRepository extends JpaRepository<NasaEntity, Long> {


    List<NasaEntity> findAllByOrderByIdDesc();



}
