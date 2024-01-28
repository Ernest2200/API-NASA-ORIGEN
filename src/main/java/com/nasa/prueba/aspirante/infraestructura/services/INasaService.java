package com.nasa.prueba.aspirante.infraestructura.services;

import com.nasa.prueba.aspirante.dominio.entities.NasaEntity;

import java.util.List;

public interface INasaService {
    public List<NasaEntity> getAllNasaEntitiesDescendingOrderById();


}
