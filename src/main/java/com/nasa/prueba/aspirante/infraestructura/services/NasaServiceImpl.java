package com.nasa.prueba.aspirante.infraestructura.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.prueba.aspirante.dominio.entities.NasaEntity;
import com.nasa.prueba.aspirante.infraestructura.repository.INasaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NasaServiceImpl implements INasaService {

    private final INasaRepository nasaRepository;


    @Override
    public List<NasaEntity> getAllNasaEntitiesDescendingOrderById() {
        return nasaRepository.findAllByOrderByIdDesc();


    }


}

