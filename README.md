# 🎉 Sistema de Gestión de Eventos Perú - Backend

Sistema de gestión de eventos desarrollado con arquitectura orientada a servicios (SOA) utilizando Spring Boot.

## 📋 Requisitos Previos

- Java 21+
- PostgreSQL 14+
- Maven 3.8+

## 🚀 Configuración Inicial

### 1. Crear la Base de Datos

```sql
CREATE DATABASE eventos_peru_db;
```

### 2. Configurar Credenciales

Edita el archivo `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/eventos_peru_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

# Mail (opcional - para notificaciones)
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-password-app
```

### 3. Compilar y Ejecutar

```bash
# Compilar el proyecto
mvnw clean install

# Ejecutar la aplicación
mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 🏗️ Arquitectura del Proyecto

### Servicios Implementados

1. **Autenticación y Gestión de Usuarios** (`/api/auth`)
   - Registro de usuarios
   - Login con JWT
   - Recuperación de contraseña
   - Gestión de roles (CLIENTE, PROVEEDOR, ADMIN)

2. **Gestión de Clientes** (`/api/clientes`)
   - CRUD de clientes
   - Consulta de perfil
   - Historial de eventos

3. **Gestión de Proveedores/Encargados** (`/api/encargados`)
   - Registro y actualización de encargados
   - Consulta de disponibilidad
   - Gestión de calificaciones

4. **Gestión de Eventos** (`/api/eventos`)
   - Crear eventos
   - Asignar encargados
   - Actualizar estados
   - Cerrar eventos

5. **Seguimiento y Control** (`/api/seguimientos`)
   - Registrar seguimientos
   - Agregar evidencias
   - Consultar status en tiempo real

6. **Notificaciones** (`/api/notificaciones`)
   - Envío de correos
   - Confirmaciones de registro
   - Notificaciones de cambio de estado
   - Recordatorios

7. **Evaluación y Retroalimentación** (`/api/comentarios`)
   - Calificar eventos
   - Consultar feedback
   - Reportes de satisfacción

## 🔐 Autenticación

El sistema utiliza JWT (JSON Web Tokens) para la autenticación. 

### Flujo de Autenticación

1. **Registro**
```json
POST /api/auth/registro
{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "password": "password123",
  "telefono": "987654321",
  "rol": "CLIENTE"
}
```

2. **Login**
```json
POST /api/auth/login
{
  "email": "juan@example.com",
  "password": "password123"
}
```

Respuesta:
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "tipo": "Bearer",
    "id": 1,
    "email": "juan@example.com",
    "nombre": "Juan Pérez",
    "roles": ["ROLE_CLIENTE"]
  }
}
```

3. **Usar el Token**

En todas las peticiones protegidas, incluir el header:
```
Authorization: Bearer <token>
```

## 📊 Modelo de Datos

### Roles del Sistema

- **ROLE_CLIENTE**: Clientes que solicitan eventos
- **ROLE_PROVEEDOR**: Encargados que ejecutan eventos
- **ROLE_ADMIN**: Administradores del sistema

### Estados de Eventos

- **PENDIENTE**: Evento creado, esperando asignación
- **EN_EJECUCION**: Evento en proceso
- **FINALIZADO**: Evento completado
- **CANCELADO**: Evento cancelado

## 🧪 Endpoints Principales

### Autenticación
- `POST /api/auth/registro` - Registrar usuario
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/recuperar-password` - Solicitar recuperación
- `POST /api/auth/reset-password` - Resetear contraseña

### Clientes
- `POST /api/clientes` - Crear cliente
- `GET /api/clientes/perfil` - Obtener perfil actual
- `PUT /api/clientes/{id}` - Actualizar cliente
- `GET /api/clientes/{id}/historial` - Historial de eventos

### Encargados
- `POST /api/encargados` - Registrar encargado
- `GET /api/encargados/disponibles` - Listar disponibles
- `GET /api/encargados/especialidad/{especialidad}` - Buscar por especialidad
- `PATCH /api/encargados/{id}/disponibilidad` - Actualizar disponibilidad

### Eventos
- `POST /api/eventos` - Crear evento
- `PATCH /api/eventos/{id}/asignar` - Asignar encargado
- `PATCH /api/eventos/{id}/estado` - Actualizar estado
- `POST /api/eventos/{id}/cerrar` - Cerrar evento
- `GET /api/eventos/{id}` - Obtener detalle

### Seguimiento
- `POST /api/seguimientos` - Crear seguimiento
- `GET /api/seguimientos/evento/{eventoId}` - Obtener seguimientos
- `PATCH /api/seguimientos/{id}/evidencia` - Agregar evidencia

### Comentarios
- `POST /api/comentarios` - Crear comentario
- `GET /api/comentarios/encargado/{encargadoId}/reporte` - Reporte de satisfacción

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.5.6**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Spring Mail**
- **Jakarta Validation**

## 📝 Notas Importantes

### Configuración de Email

Para que funcione el envío de correos, necesitas:

1. Una cuenta de Gmail
2. Habilitar "Verificación en dos pasos"
3. Generar una "Contraseña de aplicación"
4. Configurar las credenciales en `application.properties`

### Inicialización de Datos

Al iniciar la aplicación por primera vez, se crean automáticamente:
- 3 roles: ROLE_CLIENTE, ROLE_PROVEEDOR, ROLE_ADMIN
- Permisos básicos del sistema

### CORS

El backend está configurado para aceptar peticiones desde:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)

Puedes modificar esto en `application.properties`:
```properties
cors.allowed-origins=http://localhost:3000,http://localhost:4200
```

## 🐛 Troubleshooting

### Error de Conexión a PostgreSQL

Verifica que:
1. PostgreSQL esté ejecutándose
2. La base de datos existe
3. Las credenciales son correctas

### Error de JWT

Si los tokens no funcionan, verifica:
1. El secreto JWT en `application.properties`
2. Que el token no haya expirado (24 horas)
3. Que el header Authorization esté bien formateado

## 📧 Contacto y Soporte

Para dudas o problemas, contacta al equipo de desarrollo.

---

**Desarrollado con ❤️ para Eventos Perú**
