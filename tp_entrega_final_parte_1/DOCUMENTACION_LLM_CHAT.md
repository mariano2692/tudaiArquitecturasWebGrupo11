# Documentación del Servicio de Chat con LLM

## Descripción General

El sistema implementa un **asistente virtual inteligente** para usuarios premium del servicio de monopatines eléctricos compartidos. Utiliza el modelo **Llama 3.3 70B** de Groq como LLM (Large Language Model) con capacidad de **function calling** (tool use) para consultar información en tiempo real de los microservicios.

## Arquitectura

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   Usuario       │────▶│    Gateway      │────▶│   Groq API      │
│   Premium       │     │   (ChatService) │     │   (LLM)         │
└─────────────────┘     └────────┬────────┘     └─────────────────┘
                                 │
                    ┌────────────┼────────────┐
                    ▼            ▼            ▼
            ┌───────────┐ ┌───────────┐ ┌───────────┐
            │ Usuarios  │ │Monopatines│ │  Viajes   │
            │   :8083   │ │   :8080   │ │   :8082   │
            └───────────┘ └───────────┘ └───────────┘
```

## Componentes

### 1. ChatController
- **Endpoint**: `POST /api/chat`
- **Seguridad**: Requiere JWT con autoridad `PREMIUM`
- **Swagger**: Documentado con OpenAPI 3.0

### 2. ChatService
- Integración con API de Groq
- Manejo de conversaciones con contexto
- Procesamiento de tool calls

### 3. ToolService
- Ejecuta las herramientas disponibles
- Conecta con los microservicios del sistema

## Configuración

### application.yml
```yaml
# Groq LLM Configuration
groq:
  api:
    key: gsk_xxxxx  # Tu API key de Groq
    url: https://api.groq.com/openai/v1/chat/completions
  model: llama-3.3-70b-versatile

# URLs de microservicios
microservices:
  usuarios:
    url: http://localhost:8083
  monopatines:
    url: http://localhost:8080
  viajes:
    url: http://localhost:8082
```

## Herramientas Disponibles (Tools)

El asistente puede ejecutar las siguientes herramientas automáticamente según la consulta del usuario:

| Tool | Descripción | Parámetros |
|------|-------------|------------|
| `get_monopatines_cercanos` | Busca monopatines disponibles cerca de una ubicación | `latitud`, `longitud`, `radio_km` |
| `get_uso_cuenta` | Historial de uso de una cuenta | `id_cuenta`, `fecha_inicio`, `fecha_fin` |
| `get_tarifa_actual` | Tarifa vigente del servicio | - |
| `get_info_cuenta` | Información completa de una cuenta | `id_cuenta` |
| `get_saldo_cuenta` | Saldo disponible de una cuenta | `id_cuenta` |
| `calcular_precio_viaje` | Estima el costo de un viaje | `km_estimados`, `minutos_estimados`, `minutos_pausa_estimados` |
| `get_reporte_uso_monopatines` | Reporte de uso de la flota | `incluir_pausas` |
| `get_usuarios_mas_activos` | Ranking de usuarios activos | `fecha_inicio`, `fecha_fin` |

---

## Endpoints

### Health Check
```
GET /api/chat/health
```
No requiere autenticación.

### Enviar Mensaje al Chat
```
POST /api/chat
```
Requiere JWT con rol `PREMIUM`.

---

## cURLs de Ejemplo

### 1. Obtener Token JWT (Login)

Primero, necesitas autenticarte para obtener el token:

```bash
curl -X POST http://localhost:8090/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@email.com",
    "password": "password123"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer"
}
```

### 2. Health Check del Chat

```bash
curl -X GET http://localhost:8090/api/chat/health
```

**Respuesta:**
```
Chat service is running
```

### 3. Consulta General al Asistente

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "Hola, ¿qué servicios puedes ofrecerme?"
  }'
```

**Respuesta:**
```json
{
  "respuesta": "¡Hola! Soy tu asistente virtual del servicio de monopatines eléctricos. Puedo ayudarte con:\n\n- Buscar monopatines disponibles cerca de tu ubicación\n- Consultar el historial de uso de tu cuenta\n- Ver las tarifas actuales\n- Estimar el costo de un viaje\n- Consultar tu saldo disponible\n\n¿En qué puedo ayudarte?",
  "exito": true,
  "error": null
}
```

### 4. Buscar Monopatines Cercanos

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "¿Hay monopatines disponibles cerca de la ubicación -37.3217, -59.1346?"
  }'
```

### 5. Consultar Tarifa Actual

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "¿Cuál es la tarifa actual del servicio?"
  }'
```

### 6. Consultar Saldo de Cuenta

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "¿Cuál es el saldo de mi cuenta?",
    "idCuenta": 1
  }'
```

### 7. Estimar Precio de Viaje

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "¿Cuánto me costaría un viaje de 5 kilómetros con 30 minutos de duración?"
  }'
```

### 8. Consultar Historial de Uso

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "¿Cuántos viajes he realizado este año?",
    "idCuenta": 1
  }'
```

### 9. Información Completa de Cuenta

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "Dame toda la información de mi cuenta",
    "idCuenta": 1
  }'
```

### 10. Reporte de Uso de Flota (Admin Premium)

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "Necesito un reporte del uso de todos los monopatines de la flota"
  }'
```

### 11. Usuarios Más Activos

```bash
curl -X POST http://localhost:8090/api/chat \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TU_TOKEN_JWT>" \
  -d '{
    "mensaje": "¿Quiénes son los usuarios más activos del sistema?"
  }'
```

---

## Estructura de Request/Response

### ChatRequest
```json
{
  "mensaje": "string (requerido)",
  "idCuenta": "number (opcional)"
}
```

### ChatResponse
```json
{
  "respuesta": "string",
  "exito": "boolean",
  "error": "string | null"
}
```

---

## Flujo de Function Calling

```
1. Usuario envía mensaje
         │
         ▼
2. ChatService construye prompt con contexto
         │
         ▼
3. Primera llamada a Groq con tools disponibles
         │
         ▼
4. ¿Groq solicita tool_calls?
    │           │
   NO          SÍ
    │           │
    ▼           ▼
5a. Retorna    5b. ToolService ejecuta
    respuesta       las herramientas
    directa              │
                         ▼
                   6. Agrega resultados
                      al contexto
                         │
                         ▼
                   7. Segunda llamada a Groq
                      con resultados
                         │
                         ▼
                   8. Retorna respuesta
                      final al usuario
```

---

## Códigos de Error

| Código HTTP | Descripción |
|-------------|-------------|
| 200 | Respuesta exitosa |
| 400 | Error en el procesamiento (ver campo `error`) |
| 401 | No autenticado (falta token JWT) |
| 403 | No autorizado (usuario no es PREMIUM) |

---

## Ejemplos de Conversación

### Ejemplo 1: Consulta de tarifas
**Usuario:** "¿Cuánto cuesta usar el servicio?"

**Asistente:** "Las tarifas actuales del servicio son:
- Precio base: $150 ARS
- Precio por kilómetro: $50 ARS
- Precio por minuto de pausa: $25 ARS

Por ejemplo, un viaje de 3 km sin pausas te costaría aproximadamente $300 ARS."

### Ejemplo 2: Búsqueda de monopatines
**Usuario:** "Busco monopatines cerca de (-37.32, -59.13)"

**Asistente:** "Encontré 3 monopatines disponibles cerca de tu ubicación:
1. Monopatín #15 - 200m de distancia - Batería: 85%
2. Monopatín #23 - 350m de distancia - Batería: 92%
3. Monopatín #8 - 500m de distancia - Batería: 78%

¿Te gustaría que estime el costo de un viaje?"

---

## Notas de Seguridad

1. **Autenticación**: El endpoint `/api/chat` requiere un token JWT válido
2. **Autorización**: Solo usuarios con rol `PREMIUM` pueden acceder
3. **API Key de Groq**: Se almacena en `application.yml` (considerar usar variables de entorno en producción)
4. **Validación**: El mensaje no puede estar vacío (`@NotBlank`)

---

## Obtener API Key de Groq

1. Ir a https://console.groq.com/keys
2. Crear una cuenta o iniciar sesión
3. Generar una nueva API key
4. Copiar la key (formato: `gsk_xxxxx`)
5. Configurar en `application.yml`

---

## Modelos Disponibles en Groq

| Modelo | Descripción |
|--------|-------------|
| `llama-3.3-70b-versatile` | Recomendado - Balance entre rendimiento y velocidad |
| `llama-3.1-70b-versatile` | Alternativa estable |
| `llama-3.1-8b-instant` | Más rápido, menor capacidad |
| `mixtral-8x7b-32768` | Contexto extendido |

---

## Troubleshooting

### Error: "API key de Groq no configurada"
- Verificar que `groq.api.key` esté configurado en `application.yml`
- Reiniciar el gateway después de cambiar la configuración

### Error: "model has been decommissioned"
- Actualizar `groq.model` a un modelo vigente (`llama-3.3-70b-versatile`)
- Consultar https://console.groq.com/docs/deprecations

### Error: 403 Forbidden
- Verificar que el usuario tenga rol `PREMIUM`
- Verificar que el token JWT sea válido y no haya expirado
