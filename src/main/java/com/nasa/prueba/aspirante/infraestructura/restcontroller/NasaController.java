package com.nasa.prueba.aspirante.infraestructura.restcontroller;

import com.nasa.prueba.aspirante.dominio.entities.NasaEntity;
import com.nasa.prueba.aspirante.infraestructura.services.INasaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/nasa")
@RequiredArgsConstructor
public class NasaController {
    private final INasaService nasaService;


    @GetMapping("/list")
    public List<NasaEntity> getAllNasaEntities() {
        return nasaService.getAllNasaEntitiesDescendingOrderById();
    }
}
