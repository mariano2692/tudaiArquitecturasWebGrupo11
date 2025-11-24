package org.example.gateway.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ToolService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${microservices.usuarios.url:http://localhost:8083}")
    private String usuariosUrl;

    @Value("${microservices.monopatines.url:http://localhost:8080}")
    private String monopatinesUrl;

    @Value("${microservices.viajes.url:http://localhost:8082}")
    private String viajesUrl;

    public ToolService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Obtiene monopatines cercanos a una ubicación
     */
    public String getMonopatinesCercanos(double latitud, double longitud, double radioKm) {
        try {
            String url = String.format("%s/api/monopatines/cercanos?lat=%f&lon=%f&radioKm=%f",
                    monopatinesUrl, latitud, longitud, radioKm);
            Object response = restTemplate.getForObject(url, Object.class);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"No se pudieron obtener los monopatines cercanos: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtiene el uso de monopatines de una cuenta en un período
     */
    public String getUsoCuenta(Long idCuenta, String fechaInicio, String fechaFin) {
        try {
            String url = String.format("%s/api/viajes/uso?idCuenta=%d&fechaInicio=%s&fechaFin=%s",
                    viajesUrl, idCuenta, fechaInicio, fechaFin);
            Object response = restTemplate.getForObject(url, Object.class);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"No se pudo obtener el uso de la cuenta: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtiene la tarifa actual vigente
     */
    public String getTarifaActual() {
        try {
            String url = viajesUrl + "/api/tarifas/actual";
            Object response = restTemplate.getForObject(url, Object.class);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"No se pudo obtener la tarifa actual: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtiene información de una cuenta específica
     */
    public String getInfoCuenta(Long idCuenta) {
        try {
            String url = usuariosUrl + "/api/cuentas/" + idCuenta;
            Object response = restTemplate.getForObject(url, Object.class);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"No se pudo obtener la información de la cuenta: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtiene el saldo de una cuenta
     */
    public String getSaldoCuenta(Long idCuenta) {
        try {
            String url = usuariosUrl + "/api/cuentas/" + idCuenta;
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            if (response != null && response.has("saldo")) {
                Map<String, Object> result = new HashMap<>();
                result.put("idCuenta", idCuenta);
                result.put("saldo", response.get("saldo").asDouble());
                result.put("tipo", response.has("tipo") ? response.get("tipo").asText() : "DESCONOCIDO");
                return objectMapper.writeValueAsString(result);
            }
            return "{\"error\": \"No se encontró la cuenta\"}";
        } catch (Exception e) {
            return "{\"error\": \"No se pudo obtener el saldo: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Calcula el precio estimado de un viaje
     */
    public String calcularPrecioViaje(double kmEstimados, int minutosEstimados, int minutosPausaEstimados) {
        try {
            String tarifaJson = getTarifaActual();
            JsonNode tarifa = objectMapper.readTree(tarifaJson);

            if (tarifa.has("error")) {
                return tarifaJson;
            }

            double precioBase = tarifa.has("precioBase") ? tarifa.get("precioBase").asDouble() : 0;
            double precioPorKm = tarifa.has("precioPorKm") ? tarifa.get("precioPorKm").asDouble() : 0;
            double precioPorMinutoPausa = tarifa.has("precioPorMinutoPausa") ? tarifa.get("precioPorMinutoPausa").asDouble() : 0;

            double costoKm = kmEstimados * precioPorKm;
            double costoPausa = minutosPausaEstimados * precioPorMinutoPausa;
            double total = precioBase + costoKm + costoPausa;

            Map<String, Object> result = new HashMap<>();
            result.put("kmEstimados", kmEstimados);
            result.put("minutosEstimados", minutosEstimados);
            result.put("minutosPausaEstimados", minutosPausaEstimados);
            result.put("precioBase", precioBase);
            result.put("costoPorKm", costoKm);
            result.put("costoPorPausa", costoPausa);
            result.put("totalEstimado", total);
            result.put("moneda", "ARS");

            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            return "{\"error\": \"No se pudo calcular el precio: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtiene el reporte de uso de monopatines (flota completa)
     */
    public String getReporteUsoMonopatines(boolean incluirPausas) {
        try {
            String url = String.format("%s/api/monopatines/reporte-uso?incluirPausas=%b",
                    monopatinesUrl, incluirPausas);
            Object response = restTemplate.getForObject(url, Object.class);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"No se pudo obtener el reporte: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Obtiene los usuarios más activos en un período
     */
    public String getUsuariosMasActivos(String fechaInicio, String fechaFin) {
        try {
            String url = String.format("%s/api/viajes/usuarios-mas-activos?fechaInicio=%s&fechaFin=%s",
                    viajesUrl, fechaInicio, fechaFin);
            Object response = restTemplate.getForObject(url, Object.class);
            return objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            return "{\"error\": \"No se pudieron obtener los usuarios más activos: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Ejecuta un tool basado en el nombre y argumentos
     */
    public String executeTool(String toolName, Map<String, Object> arguments) {
        return switch (toolName) {
            case "get_monopatines_cercanos" -> {
                double lat = ((Number) arguments.getOrDefault("latitud", 0.0)).doubleValue();
                double lon = ((Number) arguments.getOrDefault("longitud", 0.0)).doubleValue();
                double radio = ((Number) arguments.getOrDefault("radio_km", 1.0)).doubleValue();
                yield getMonopatinesCercanos(lat, lon, radio);
            }
            case "get_uso_cuenta" -> {
                Long idCuenta = ((Number) arguments.get("id_cuenta")).longValue();
                String fechaInicio = (String) arguments.getOrDefault("fecha_inicio", "2024-01-01T00:00:00");
                String fechaFin = (String) arguments.getOrDefault("fecha_fin", "2024-12-31T23:59:59");
                yield getUsoCuenta(idCuenta, fechaInicio, fechaFin);
            }
            case "get_tarifa_actual" -> getTarifaActual();
            case "get_info_cuenta" -> {
                Long idCuenta = ((Number) arguments.get("id_cuenta")).longValue();
                yield getInfoCuenta(idCuenta);
            }
            case "get_saldo_cuenta" -> {
                Long idCuenta = ((Number) arguments.get("id_cuenta")).longValue();
                yield getSaldoCuenta(idCuenta);
            }
            case "calcular_precio_viaje" -> {
                double km = ((Number) arguments.getOrDefault("km_estimados", 0.0)).doubleValue();
                int minutos = ((Number) arguments.getOrDefault("minutos_estimados", 0)).intValue();
                int pausa = ((Number) arguments.getOrDefault("minutos_pausa_estimados", 0)).intValue();
                yield calcularPrecioViaje(km, minutos, pausa);
            }
            case "get_reporte_uso_monopatines" -> {
                boolean incluirPausas = (Boolean) arguments.getOrDefault("incluir_pausas", true);
                yield getReporteUsoMonopatines(incluirPausas);
            }
            case "get_usuarios_mas_activos" -> {
                String fechaInicio = (String) arguments.getOrDefault("fecha_inicio", "2024-01-01T00:00:00");
                String fechaFin = (String) arguments.getOrDefault("fecha_fin", "2024-12-31T23:59:59");
                yield getUsuariosMasActivos(fechaInicio, fechaFin);
            }
            default -> "{\"error\": \"Tool no reconocido: " + toolName + "\"}";
        };
    }
}
