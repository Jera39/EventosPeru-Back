# 📖 Guía de Uso - API Eventos Perú

## 🔐 1. Autenticación

### Registrar un Cliente

```bash
POST http://localhost:8080/api/auth/registro
Content-Type: application/json

{
  "nombre": "María González",
  "email": "maria@example.com",
  "password": "password123",
  "telefono": "987654321",
  "rol": "CLIENTE"
}
```

### Registrar un Proveedor

```bash
POST http://localhost:8080/api/auth/registro
Content-Type: application/json

{
  "nombre": "Carlos Rodríguez",
  "email": "carlos@example.com",
  "password": "password123",
  "telefono": "987654322",
  "rol": "PROVEEDOR"
}
```

### Iniciar Sesión

```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "maria@example.com",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJpYUBleGFtcGxlLmNvbSIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDg2NDAwfQ.xxxxx",
    "tipo": "Bearer",
    "id": 1,
    "email": "maria@example.com",
    "nombre": "María González",
    "roles": ["ROLE_CLIENTE"]
  }
}
```

## 👤 2. Gestión de Clientes

**Nota:** Incluir el token en todas las peticiones:
```
Authorization: Bearer <token>
```

### Crear Perfil de Cliente

```bash
POST http://localhost:8080/api/clientes
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombre": "María González",
  "email": "maria@example.com",
  "telefono": "987654321"
}
```

### Obtener Perfil Actual

```bash
GET http://localhost:8080/api/clientes/perfil
Authorization: Bearer <token>
```

### Actualizar Cliente

```bash
PUT http://localhost:8080/api/clientes/1
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombre": "María González Pérez",
  "email": "maria@example.com",
  "telefono": "987654321"
}
```

## 👷 3. Gestión de Encargados

### Registrar Encargado

```bash
POST http://localhost:8080/api/encargados
Authorization: Bearer <token>
Content-Type: application/json

{
  "nombre": "Carlos Rodríguez",
  "especialidad": "Fotografía",
  "experiencia": "10 años de experiencia en eventos corporativos y sociales",
  "telefono": "987654322"
}
```

### Buscar Encargados Disponibles

```bash
GET http://localhost:8080/api/encargados/disponibles
Authorization: Bearer <token>
```

### Buscar por Especialidad

```bash
GET http://localhost:8080/api/encargados/disponibles/especialidad/Fotografía
Authorization: Bearer <token>
```

### Actualizar Disponibilidad

```bash
PATCH http://localhost:8080/api/encargados/1/disponibilidad?disponible=false
Authorization: Bearer <token>
```

## 🎉 4. Gestión de Eventos

### Crear Evento

```bash
POST http://localhost:8080/api/eventos
Authorization: Bearer <token>
Content-Type: application/json

{
  "titulo": "Boda de Ana y Luis",
  "descripcion": "Ceremonia y recepción en el Club Campestre",
  "fechaInicio": "2025-12-15T10:00:00",
  "fechaFin": "2025-12-15T23:00:00",
  "detallePlanificacion": "Ceremonia a las 10:00, recepción a las 14:00"
}
```

### Asignar Encargado a Evento

```bash
PATCH http://localhost:8080/api/eventos/1/asignar
Authorization: Bearer <token>
Content-Type: application/json

{
  "encargadoId": 1
}
```

### Actualizar Estado de Evento

```bash
PATCH http://localhost:8080/api/eventos/1/estado
Authorization: Bearer <token>
Content-Type: application/json

{
  "estado": "EN_EJECUCION"
}
```

Estados válidos:
- `PENDIENTE`
- `EN_EJECUCION`
- `FINALIZADO`
- `CANCELADO`

### Consultar Evento

```bash
GET http://localhost:8080/api/eventos/1
Authorization: Bearer <token>
```

### Listar Eventos de un Cliente

```bash
GET http://localhost:8080/api/eventos/cliente/1
Authorization: Bearer <token>
```

### Cerrar Evento

```bash
POST http://localhost:8080/api/eventos/1/cerrar
Authorization: Bearer <token>
Content-Type: text/plain

El evento se realizó exitosamente. Todos los servicios fueron entregados según lo acordado.
```

## 📊 5. Seguimiento y Control

### Crear Seguimiento

```bash
POST http://localhost:8080/api/seguimientos
Authorization: Bearer <token>
Content-Type: application/json

{
  "eventoId": 1,
  "estadoActual": "En preparación",
  "observaciones": "Se están coordinando los detalles finales con el cliente"
}
```

### Agregar Evidencia

```bash
PATCH http://localhost:8080/api/seguimientos/1/evidencia
Authorization: Bearer <token>
Content-Type: text/plain

https://ejemplo.com/fotos/evento1/foto1.jpg
```

### Consultar Seguimientos de un Evento

```bash
GET http://localhost:8080/api/seguimientos/evento/1
Authorization: Bearer <token>
```

### Obtener Último Seguimiento

```bash
GET http://localhost:8080/api/seguimientos/evento/1/ultimo
Authorization: Bearer <token>
```

## ⭐ 6. Evaluación y Retroalimentación

### Calificar Evento

```bash
POST http://localhost:8080/api/comentarios
Authorization: Bearer <token>
Content-Type: application/json

{
  "eventoId": 1,
  "calificacion": 5,
  "opinion": "Excelente servicio, muy profesional. Superaron nuestras expectativas."
}
```

**Nota:** La calificación debe estar entre 1 y 5.

### Consultar Comentarios de un Evento

```bash
GET http://localhost:8080/api/comentarios/evento/1
Authorization: Bearer <token>
```

### Consultar Feedback de un Encargado

```bash
GET http://localhost:8080/api/comentarios/encargado/1
Authorization: Bearer <token>
```

### Generar Reporte de Satisfacción

```bash
GET http://localhost:8080/api/comentarios/encargado/1/reporte
Authorization: Bearer <token>
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Reporte de satisfacción generado exitosamente",
  "data": {
    "encargadoId": 1,
    "encargadoNombre": "Carlos Rodríguez",
    "calificacionPromedio": 4.8,
    "totalComentarios": 5,
    "comentarios": [...]
  }
}
```

## 📧 7. Notificaciones

### Enviar Correo Personalizado (Solo Admin)

```bash
POST http://localhost:8080/api/notificaciones/enviar
Authorization: Bearer <token>
Content-Type: application/json

{
  "destinatario": "cliente@example.com",
  "asunto": "Confirmación de Evento",
  "mensaje": "Tu evento ha sido confirmado para el día 15 de diciembre."
}
```

## 🔄 8. Flujo Completo de un Evento

### Paso 1: Cliente se registra y crea un evento

```bash
# Registro
POST /api/auth/registro
{
  "nombre": "Ana Martínez",
  "email": "ana@example.com",
  "password": "password123",
  "telefono": "987654323",
  "rol": "CLIENTE"
}

# Login
POST /api/auth/login
{
  "email": "ana@example.com",
  "password": "password123"
}

# Crear evento
POST /api/eventos
Authorization: Bearer <token>
{
  "titulo": "Quinceañero de Sofía",
  "descripcion": "Fiesta de 15 años",
  "fechaInicio": "2025-11-20T18:00:00",
  "fechaFin": "2025-11-21T02:00:00"
}
```

### Paso 2: Cliente busca y asigna un encargado

```bash
# Buscar encargados disponibles
GET /api/encargados/disponibles
Authorization: Bearer <token>

# Asignar encargado
PATCH /api/eventos/1/asignar
Authorization: Bearer <token>
{
  "encargadoId": 1
}
```

### Paso 3: Encargado gestiona el evento

```bash
# Cambiar estado a EN_EJECUCION
PATCH /api/eventos/1/estado
Authorization: Bearer <token_encargado>
{
  "estado": "EN_EJECUCION"
}

# Crear seguimiento
POST /api/seguimientos
Authorization: Bearer <token_encargado>
{
  "eventoId": 1,
  "estadoActual": "Iniciando montaje",
  "observaciones": "Todo va según lo planificado"
}
```

### Paso 4: Finalizar evento

```bash
# Cerrar evento
POST /api/eventos/1/cerrar
Authorization: Bearer <token_encargado>
Content-Type: text/plain

Evento finalizado exitosamente. Cliente satisfecho.
```

### Paso 5: Cliente califica el servicio

```bash
# Crear comentario
POST /api/comentarios
Authorization: Bearer <token_cliente>
{
  "eventoId": 1,
  "calificacion": 5,
  "opinion": "Todo estuvo perfecto, muy recomendado"
}
```

## 🛡️ Roles y Permisos

### ROLE_CLIENTE
- Crear y gestionar sus propios eventos
- Asignar encargados a eventos
- Calificar eventos finalizados
- Consultar su historial

### ROLE_PROVEEDOR
- Gestionar su perfil de encargado
- Ver eventos asignados
- Actualizar estado de eventos
- Crear seguimientos
- Cerrar eventos

### ROLE_ADMIN
- Acceso completo a todos los endpoints
- Gestionar todos los usuarios
- Consultar todos los eventos
- Enviar notificaciones
- Ver reportes globales

## ⚠️ Errores Comunes

### 401 Unauthorized
```json
{
  "timestamp": "2025-10-15T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token inválido o expirado"
}
```
**Solución:** Verificar que el token sea válido y esté incluido en el header.

### 403 Forbidden
```json
{
  "timestamp": "2025-10-15T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "message": "Acceso denegado"
}
```
**Solución:** Verificar que el usuario tenga los permisos necesarios.

### 400 Bad Request
```json
{
  "timestamp": "2025-10-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "calificacion": "La calificación debe estar entre 1 y 5"
  }
}
```
**Solución:** Revisar los datos enviados y corregir según los mensajes de error.

---

**💡 Tip:** Usa herramientas como Postman o Thunder Client para probar la API de manera más cómoda.
