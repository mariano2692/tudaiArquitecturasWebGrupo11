# Microservicio de Usuarios y Cuentas

Microservicio para la gestión de usuarios y cuentas del sistema de alquiler de monopatines.

## Tecnologías
- Spring Boot 3.3.5
- Spring Data JPA
- MySQL / H2 (desarrollo)
- Eureka Client
- Lombok

## Puerto
- **8081**

## Base de Datos
- **MySQL**: `db_usuarios`
- **Usuario**: root
- **Password**: (vacío por defecto)

## Entidades

### Usuario
- `id`: Long (PK)
- `nombre`: String
- `apellido`: String
- `email`: String (único)
- `celular`: String
- Relación ManyToMany con Cuenta

### Cuenta
- `id`: Long (PK)
- `fechaAlta`: LocalDate
- `tipo`: TipoCuenta (BASICA, PREMIUM)
- `saldo`: Double
- `cuentaMercadoPago`: String
- `cupoMensualUsado`: Double
- `activa`: Boolean
- Relación ManyToMany con Usuario

## Endpoints

### Usuarios - `/api/usuarios`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/usuarios` | Listar todos los usuarios |
| GET | `/api/usuarios/{id}` | Obtener usuario por ID |
| POST | `/api/usuarios` | Crear nuevo usuario |
| PUT | `/api/usuarios/{id}` | Actualizar usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario |

### Cuentas - `/api/cuentas`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cuentas` | Listar todas las cuentas |
| GET | `/api/cuentas/{id}` | Obtener cuenta por ID |
| POST | `/api/cuentas` | Crear nueva cuenta |
| PUT | `/api/cuentas/{id}` | Actualizar cuenta |
| DELETE | `/api/cuentas/{id}` | Eliminar cuenta |
| PATCH | `/api/cuentas/{id}/anular` | Anular cuenta (punto 4.b) |
| PATCH | `/api/cuentas/{id}/activar` | Activar cuenta |
| POST | `/api/cuentas/{cuentaId}/usuarios/{usuarioId}` | Asociar usuario a cuenta |
| DELETE | `/api/cuentas/{cuentaId}/usuarios/{usuarioId}` | Desasociar usuario de cuenta |
| PATCH | `/api/cuentas/{id}/cargar-saldo?monto={monto}` | Cargar saldo a cuenta |
| GET | `/api/cuentas/tipo/{tipo}` | Filtrar cuentas por tipo (BASICA/PREMIUM) |
| GET | `/api/cuentas/activas` | Obtener solo cuentas activas |
| GET | `/api/cuentas/inactivas` | Obtener solo cuentas inactivas |

## Ejemplos de Request

### Crear Usuario
```json
POST /api/usuarios
{
  "nombre": "Juan",
  "apellido": "Perez",
  "email": "juan.perez@example.com",
  "celular": "2494123456"
}
```

### Crear Cuenta
```json
POST /api/cuentas
{
  "tipo": "PREMIUM",
  "saldo": 10000.00,
  "cuentaMercadoPago": "MP001"
}
```

### Asociar Usuario a Cuenta
```
POST /api/cuentas/1/usuarios/1
```

### Anular Cuenta (Punto 4.b de la consigna)
```
PATCH /api/cuentas/1/anular
```

## Ejecución

```bash
cd microServicio1
mvn spring-boot:run
```

El servicio se registrará automáticamente en Eureka (puerto 8761).

## Datos de Prueba

El archivo `data.sql` contiene datos de prueba que se cargan automáticamente:
- 4 usuarios
- 5 cuentas (4 activas, 1 inactiva)
- Relaciones usuario-cuenta establecidas
