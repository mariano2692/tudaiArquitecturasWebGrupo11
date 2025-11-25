package com.viajes.client;

import com.viajes.dto.CuentaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CuentaClient {

    private final RestTemplate restTemplate;

    @Value("${microservicio.usuarios.url:http://localhost:8083}")
    private String usuariosServiceUrl;

    public CuentaClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CuentaDTO getCuentaById(Long id) {
        try {
            String url = usuariosServiceUrl + "/api/cuentas/" + id;
            return restTemplate.getForObject(url, CuentaDTO.class);
        } catch (Exception e) {
            // Si falla la llamada, retornar null o un DTO vacío
            return null;
        }
    }
}
