package com.nasa.prueba.aspirante.aplicacion.taskscheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasa.prueba.aspirante.dominio.entities.NasaEntity;
import com.nasa.prueba.aspirante.infraestructura.clientrest.PruebaClienteRest;
import com.nasa.prueba.aspirante.infraestructura.repository.INasaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NasaTask {

    private final INasaRepository nasaRepository;
    private final ObjectMapper objectMapper;
    private final PruebaClienteRest nasaTask;

    @Scheduled(fixedRate = 60000) // Ejecutar cada 1 minuto (60000 milisegundos)
    public void fetchDataFromNasaApiAndSaveToDb() {
        try {

            String jsonResponse = nasaTask.fetchDataFromNasaApi();
            // Deserializar la respuesta JSON a un objeto genérico de Map
            Map<String, Object> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {
            });

            // Verificar si 'collection' es un objeto o una lista
            Object collectionObject = responseMap.get("collection");
            if (collectionObject instanceof Map) {
                // Es un objeto, obtén la lista 'items' del objeto 'collection'
                Map<String, Object> collectionMap = (Map<String, Object>) collectionObject;
                List<Map<String, Object>> items = (List<Map<String, Object>>) collectionMap.get("items");

                // Continuar con el procesamiento como antes
                processItems(items);
            } else {
                // 'collection' es una lista, obtén la lista 'items' directamente
                List<Map<String, Object>> items = (List<Map<String, Object>>) responseMap.get("collection.items");

                // Continuar con el procesamiento como antes
                processItems(items);
            }
        } catch (Exception e) {
            // Manejo de errores: Loguea la excepción o toma acciones adicionales según tus necesidades.
            e.printStackTrace();
        }
    }

    private void processItems(List<Map<String, Object>> items) {
        if (items == null) {
            // Loguea un mensaje o toma alguna acción según tus necesidades
            System.out.println("La propiedad 'items' es nula en la respuesta JSON.");
            return;
        }


        List<NasaEntity> entitiesToSave = items.stream()
                .map(item -> {
                    NasaEntity nasaEntity = new NasaEntity();
                    nasaEntity.setCreateAt(new Date());
                    nasaEntity.setHref((String) item.get("href"));

                    // Obtener la lista de data y extraer información de la primera posición
                    List<Map<String, Object>> data = (List<Map<String, Object>>) item.get("data");
                    if (data != null && !data.isEmpty()) {
                        Map<String, Object> firstData = data.get(0);
                        nasaEntity.setCenter((String) firstData.get("center"));
                        nasaEntity.setTitle((String) firstData.get("title"));
                        nasaEntity.setNasa_id((String) firstData.get("nasa_id"));

                    }

                    return nasaEntity;
                })
                .collect(Collectors.toList());

        // Guardar la lista en la base de datos
        nasaRepository.saveAll(entitiesToSave);
    }

}
