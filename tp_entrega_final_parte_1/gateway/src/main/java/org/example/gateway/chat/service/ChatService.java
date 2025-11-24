package org.example.gateway.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.gateway.chat.dto.ChatRequest;
import org.example.gateway.chat.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final ToolService toolService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key:}")
    private String groqApiKey;

    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String groqApiUrl;

    @Value("${groq.model:llama-3.3-70b-versatile}")
    private String groqModel;

    private static final String SYSTEM_PROMPT = """
            Eres un asistente virtual para un servicio de monopatines eléctricos compartidos.
            Tu rol es ayudar a los usuarios premium a consultar información sobre:
            - Monopatines disponibles cerca de su ubicación
            - Uso de monopatines de su cuenta (viajes, kilómetros, tiempo)
            - Precios y tarifas actuales
            - Estimación de costos de viajes
            - Saldo de su cuenta

            Responde siempre en español, de forma amigable y concisa.
            Cuando necesites información específica, usa las herramientas disponibles.
            Si el usuario pregunta algo que no está relacionado con el servicio de monopatines,
            indícale amablemente que solo puedes ayudar con consultas del servicio.
            """;

    public ChatService(ToolService toolService) {
        this.toolService = toolService;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public ChatResponse procesarMensaje(ChatRequest request) {
        try {
            if (groqApiKey == null || groqApiKey.isEmpty()) {
                return ChatResponse.error("API key de Groq no configurada. Configure groq.api.key en application.yml");
            }

            List<Map<String, Object>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", SYSTEM_PROMPT));

            // Si hay una cuenta asociada, agregar contexto
            if (request.getIdCuenta() != null) {
                String contexto = "El usuario está consultando con la cuenta ID: " + request.getIdCuenta();
                messages.add(Map.of("role", "system", "content", contexto));
            }

            messages.add(Map.of("role", "user", "content", request.getMensaje()));

            // Primera llamada a Groq con tools
            JsonNode response = callGroqWithTools(messages);

            // Procesar respuesta y ejecutar tools si es necesario
            return processGroqResponse(response, messages);

        } catch (Exception e) {
            return ChatResponse.error("Error al procesar el mensaje: " + e.getMessage());
        }
    }

    private JsonNode callGroqWithTools(List<Map<String, Object>> messages) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", groqModel);
        requestBody.set("messages", objectMapper.valueToTree(messages));
        requestBody.set("tools", getToolsDefinition());
        requestBody.put("tool_choice", "auto");

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                groqApiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        return objectMapper.readTree(response.getBody());
    }

    private ChatResponse processGroqResponse(JsonNode response, List<Map<String, Object>> messages) throws Exception {
        JsonNode choices = response.get("choices");
        if (choices == null || choices.isEmpty()) {
            return ChatResponse.error("Respuesta vacía del modelo");
        }

        JsonNode message = choices.get(0).get("message");
        JsonNode toolCalls = message.get("tool_calls");

        // Si hay tool calls, ejecutarlas
        if (toolCalls != null && !toolCalls.isEmpty()) {
            // Agregar el mensaje del asistente con tool_calls
            Map<String, Object> assistantMessage = new HashMap<>();
            assistantMessage.put("role", "assistant");
            assistantMessage.put("tool_calls", objectMapper.treeToValue(toolCalls, List.class));
            messages.add(assistantMessage);

            // Ejecutar cada tool y agregar resultados
            for (JsonNode toolCall : toolCalls) {
                String toolId = toolCall.get("id").asText();
                String toolName = toolCall.get("function").get("name").asText();
                JsonNode argsNode = objectMapper.readTree(toolCall.get("function").get("arguments").asText());

                @SuppressWarnings("unchecked")
                Map<String, Object> args = objectMapper.treeToValue(argsNode, Map.class);
                String result = toolService.executeTool(toolName, args);

                Map<String, Object> toolMessage = new HashMap<>();
                toolMessage.put("role", "tool");
                toolMessage.put("tool_call_id", toolId);
                toolMessage.put("content", result);
                messages.add(toolMessage);
            }

            // Segunda llamada para obtener respuesta final
            JsonNode finalResponse = callGroqWithTools(messages);
            JsonNode finalMessage = finalResponse.get("choices").get(0).get("message");

            String content = finalMessage.has("content") && !finalMessage.get("content").isNull()
                    ? finalMessage.get("content").asText()
                    : "No pude procesar tu consulta. Por favor, intenta de nuevo.";

            return ChatResponse.success(content);
        }

        // Si no hay tool calls, devolver la respuesta directa
        String content = message.has("content") && !message.get("content").isNull()
                ? message.get("content").asText()
                : "No pude entender tu consulta. Por favor, intenta de nuevo.";

        return ChatResponse.success(content);
    }

    private ArrayNode getToolsDefinition() {
        ArrayNode tools = objectMapper.createArrayNode();

        // Tool: Monopatines cercanos
        tools.add(createTool(
                "get_monopatines_cercanos",
                "Obtiene los monopatines disponibles cerca de una ubicación geográfica",
                Map.of(
                        "latitud", Map.of("type", "number", "description", "Latitud de la ubicación"),
                        "longitud", Map.of("type", "number", "description", "Longitud de la ubicación"),
                        "radio_km", Map.of("type", "number", "description", "Radio de búsqueda en kilómetros (default: 1)")
                ),
                List.of("latitud", "longitud")
        ));

        // Tool: Uso de cuenta
        tools.add(createTool(
                "get_uso_cuenta",
                "Obtiene el historial de uso de monopatines de una cuenta específica",
                Map.of(
                        "id_cuenta", Map.of("type", "number", "description", "ID de la cuenta del usuario"),
                        "fecha_inicio", Map.of("type", "string", "description", "Fecha de inicio del período (ISO 8601)"),
                        "fecha_fin", Map.of("type", "string", "description", "Fecha de fin del período (ISO 8601)")
                ),
                List.of("id_cuenta")
        ));

        // Tool: Tarifa actual
        tools.add(createTool(
                "get_tarifa_actual",
                "Obtiene la tarifa vigente con precios base, por kilómetro y por minuto de pausa",
                Map.of(),
                List.of()
        ));

        // Tool: Info cuenta
        tools.add(createTool(
                "get_info_cuenta",
                "Obtiene información completa de una cuenta (tipo, saldo, estado, usuarios asociados)",
                Map.of(
                        "id_cuenta", Map.of("type", "number", "description", "ID de la cuenta")
                ),
                List.of("id_cuenta")
        ));

        // Tool: Saldo cuenta
        tools.add(createTool(
                "get_saldo_cuenta",
                "Obtiene el saldo disponible de una cuenta",
                Map.of(
                        "id_cuenta", Map.of("type", "number", "description", "ID de la cuenta")
                ),
                List.of("id_cuenta")
        ));

        // Tool: Calcular precio viaje
        tools.add(createTool(
                "calcular_precio_viaje",
                "Calcula el precio estimado de un viaje basado en kilómetros y tiempo",
                Map.of(
                        "km_estimados", Map.of("type", "number", "description", "Kilómetros estimados del viaje"),
                        "minutos_estimados", Map.of("type", "number", "description", "Minutos estimados del viaje"),
                        "minutos_pausa_estimados", Map.of("type", "number", "description", "Minutos de pausa estimados (default: 0)")
                ),
                List.of("km_estimados")
        ));

        // Tool: Reporte uso monopatines (para admins premium)
        tools.add(createTool(
                "get_reporte_uso_monopatines",
                "Obtiene un reporte del uso de todos los monopatines de la flota",
                Map.of(
                        "incluir_pausas", Map.of("type", "boolean", "description", "Incluir tiempo de pausas en el reporte")
                ),
                List.of()
        ));

        // Tool: Usuarios más activos
        tools.add(createTool(
                "get_usuarios_mas_activos",
                "Obtiene un ranking de los usuarios más activos en un período",
                Map.of(
                        "fecha_inicio", Map.of("type", "string", "description", "Fecha de inicio del período (ISO 8601)"),
                        "fecha_fin", Map.of("type", "string", "description", "Fecha de fin del período (ISO 8601)")
                ),
                List.of()
        ));

        return tools;
    }

    private ObjectNode createTool(String name, String description, Map<String, Map<String, String>> properties, List<String> required) {
        ObjectNode tool = objectMapper.createObjectNode();
        tool.put("type", "function");

        ObjectNode function = objectMapper.createObjectNode();
        function.put("name", name);
        function.put("description", description);

        ObjectNode parameters = objectMapper.createObjectNode();
        parameters.put("type", "object");
        parameters.set("properties", objectMapper.valueToTree(properties));
        parameters.set("required", objectMapper.valueToTree(required));

        function.set("parameters", parameters);
        tool.set("function", function);

        return tool;
    }
}
