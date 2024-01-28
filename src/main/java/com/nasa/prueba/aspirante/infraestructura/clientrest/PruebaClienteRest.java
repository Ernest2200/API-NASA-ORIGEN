package com.nasa.prueba.aspirante.infraestructura.clientrest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Component
@RequiredArgsConstructor
public class PruebaClienteRest {

    private final RestTemplate restTemplate;
    public String fetchDataFromNasaApi() {
        String apiUrl = UriComponentsBuilder
                .fromUriString("https://images-api.nasa.gov/search")
                .queryParam("q", "apollo 11")
                .build().toUriString();
        return restTemplate.getForObject(apiUrl, String.class);
    }

}
