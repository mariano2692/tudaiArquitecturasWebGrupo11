package org.example.gateway.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.gateway.chat.dto.ChatRequest;
import org.example.gateway.chat.dto.ChatResponse;
import org.example.gateway.chat.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "Servicio de chat con IA para usuarios premium")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PREMIUM')")
    @Operation(
            summary = "Enviar mensaje al asistente",
            description = "Permite a usuarios premium consultar en lenguaje natural sobre servicios del sistema. " +
                    "Puede consultar: monopatines cercanos, uso de cuenta, tarifas, estimación de precios, etc."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta exitosa del asistente"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "No tiene permisos de usuario premium")
    })
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        ChatResponse response = chatService.procesarMensaje(request);
        if (response.isExito()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/health")
    @Operation(summary = "Verificar estado del servicio de chat")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Chat service is running");
    }
}
