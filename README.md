# tudaiArquitecturasWebGrupo11

## branch del tp entrega final 

para poder correr los microservicios de usuarios, viajes y monopatin es necesario primero correr el eurekaService



##swagger documentacion

#http://localhost:8090/swagger-ui/index.html




 Microservicios y Puertos

  | Servicio                  | Puerto | Base de Datos          | Descripción                                  |
  |---------------------------|--------|------------------------|----------------------------------------------|
  | Eureka Service            | 8761   | -                      | Servidor de descubrimiento de servicios      |
  | Gateway                   | 8090   | MySQL (auth)           | API Gateway con autenticación JWT y chat LLM |
  | Microservicio Usuarios    | 8083   | MySQL (db_usuarios)    | Gestión de usuarios y cuentas                |
  | Microservicio Monopatines | 8080   | MongoDB (monopatin-db) | Gestión de monopatines                       |
  | Microservicio Viajes      | 8082   | MySQL (db_viajes)      | Gestión de viajes y tarifas                  |


GROC:

 ## Configuración de API Key de Groq

  ### Paso 1: Obtener la API Key (gratuito)

  1. Crear cuenta gratuita en: https://console.groq.com/
  2. Ir a la sección de API Keys: https://console.groq.com/keys
  3. Click en "Create API Key"
  4. Copiar la key generada (formato: `gsk_xxxxxxxxxxxxx`)

  ### Paso 2: Configurar la variable de entorno

  **Windows (CMD):**
  ```cmd
  set GROQ_API_KEY=gsk_tu_api_key_aqui

  Windows (PowerShell):
  $env:GROQ_API_KEY="gsk_tu_api_key_aqui"

  Linux/Mac:
  export GROQ_API_KEY=gsk_tu_api_key_aqui

  Paso 3: Iniciar el Gateway

  Después de configurar la variable de entorno, iniciar el servicio Gateway. La aplicación tomará automáticamente el valor
  de GROQ_API_KEY desde el entorno.

  Nota: La variable de entorno debe estar configurada en la misma terminal/sesión donde se ejecuta el Gateway.
