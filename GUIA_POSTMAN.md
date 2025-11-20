# 🚀 GUÍA DE PRUEBAS CON POSTMAN - Eventos Perú

## 📋 Prerequisitos
1. Asegúrate de que MySQL esté corriendo en el puerto 3306
2. El schema `eventos_peru_db` debe existir
3. Ejecuta el proyecto: `.\mvnw spring-boot:run`
4. Base URL: `http://localhost:8080`

## 🎯 NUEVA FUNCIONALIDAD: CREACIÓN AUTOMÁTICA DE PERFILES

⚡ **¡IMPORTANTE!** Cuando registras un usuario con rol `CLIENTE` o `PROVEEDOR`, automáticamente se crea el perfil correspondiente:

- **Registro con rol "CLIENTE"** → Se crea automáticamente un `Cliente` asociado
- **Registro con rol "PROVEEDOR"** → Se crea automáticamente un `Encargado` asociado  
- **La respuesta incluye `clienteId` o `encargadoId`** según corresponda
- **En el login también se devuelven estos IDs** para usar en el frontend

¡Ya no necesitas crear perfiles por separado! 🚀

---

## 🔐 1. SERVICIO DE AUTENTICACIÓN (Auth)

### 1.1. Registro de Usuario Cliente
**POST** `http://localhost:8080/api/auth/registro`

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
    "nombre": "Juan Pérez",
    "email": "juan.perez@gmail.com",
    "password": "123456",
    "telefono": "987654321",
    "rol": "CLIENTE"
}
```

**Respuesta Esperada:**
```json
{
    "success": true,
    "message": "Usuario registrado exitosamente",
    "data": {
        "id": 1,
        "email": "juan.perez@gmail.com",
        "nombre": "Juan Pérez",
        "telefono": "987654321",
        "roles": ["ROLE_CLIENTE"],
        "clienteId": 1,
        "encargadoId": null,
        "mensaje": "Usuario registrado exitosamente"
    }
}
```

---

### 1.2. Registro de Usuario Proveedor
**POST** `http://localhost:8080/api/auth/registro`

**Body (JSON):**
```json
{
    "nombre": "María García",
    "email": "maria.garcia@gmail.com",
    "password": "123456",
    "telefono": "998877665",
    "rol": "PROVEEDOR"
}
```

---

### 1.3. Registro de Usuario Administrador
**POST** `http://localhost:8080/api/auth/registro`

**Body (JSON):**
```json
{
    "nombre": "Admin Sistema",
    "email": "admin@eventosperu.com",
    "password": "admin123",
    "telefono": "999888777",
    "rol": "ADMIN"
}
```

---

### 1.4. Login
**POST** `http://localhost:8080/api/auth/login`

**Body (JSON):**
```json
{
    "email": "juan.perez@gmail.com",
    "password": "123456"
}
```

**Respuesta Esperada:**
```json
{
    "success": true,
    "message": "Login exitoso",
    "data": {
        "id": 1,
        "email": "juan.perez@gmail.com",
        "nombre": "Juan Pérez",
        "telefono": "987654321",
        "roles": ["ROLE_CLIENTE"],
        "clienteId": 1,
        "encargadoId": null,
        "mensaje": "Login exitoso"
    }
}
```

---

### 1.5. Recuperar Contraseña
**POST** `http://localhost:8080/api/auth/recuperar-password`

**Body (JSON):**
```json
{
    "email": "juan.perez@gmail.com"
}
```

---

### 1.6. Resetear Contraseña
**POST** `http://localhost:8080/api/auth/reset-password`

**Body (JSON):**
```json
{
    "email": "juan.perez@gmail.com",
    "nuevaPassword": "nuevaPassword123"
}
```

---

## 👥 2. SERVICIO DE CLIENTES

### 2.1. Crear Cliente
**POST** `http://localhost:8080/api/clientes`

**Body (JSON):**
```json
{
    "nombre": "Carlos López",
    "email": "carlos.lopez@gmail.com",
    "telefono": "987123456",
    "direccion": "Av. Arequipa 1234, Lima, Perú",
    "password": "password123"
}
```

---

### 2.2. Obtener Cliente por ID
**GET** `http://localhost:8080/api/clientes/1`

---

### 2.3. Obtener Todos los Clientes
**GET** `http://localhost:8080/api/clientes`

---

### 2.4. Actualizar Cliente
**PUT** `http://localhost:8080/api/clientes/1`

**Body (JSON):**
```json
{
    "nombre": "Carlos López Actualizado",
    "telefono": "987654999",
    "direccion": "Av. Javier Prado 5678, San Isidro, Lima"
}
```

---

### 2.5. Eliminar Cliente
**DELETE** `http://localhost:8080/api/clientes/1`

---

### 2.6. Obtener Perfil de Cliente por Usuario ID
**GET** `http://localhost:8080/api/clientes/usuario/1`

---

### 2.7. Obtener Historial de Eventos
**GET** `http://localhost:8080/api/clientes/1/historial`

---

## 🏢 3. SERVICIO DE PROVEEDORES (Encargados)

### 3.1. Registrar Encargado
**POST** `http://localhost:8080/api/encargados`

**Body (JSON):**
```json
{
    "nombre": "Pedro Martínez",
    "email": "pedro.martinez@gmail.com",
    "telefono": "965432187",
    "password": "password123",
    "especialidad": "Catering",
    "experiencia": 5
}
```

---

### 3.2. Obtener Encargado por ID
**GET** `http://localhost:8080/api/encargados/1`

---

### 3.3. Obtener Todos los Encargados
**GET** `http://localhost:8080/api/encargados`

---

### 3.4. Actualizar Disponibilidad
**PATCH** `http://localhost:8080/api/encargados/1/disponibilidad`

**Body (JSON):**
```json
{
    "disponible": false
}
```

---

### 3.5. Obtener Calificación de un Encargado
**GET** `http://localhost:8080/api/encargados/1/calificacion`

**Respuesta Esperada:**
```json
{
    "success": true,
    "message": "Calificación obtenida exitosamente",
    "data": {
        "encargadoId": 1,
        "encargadoNombre": "Pedro Martínez",
        "calificacionPromedio": 4.5,
        "totalCalificaciones": 10
    }
}
```

**Nota:** La calificación se actualiza automáticamente cuando un cliente crea un comentario (ver sección 7.1).

---

### 3.6. Consultar Encargados Disponibles
**GET** `http://localhost:8080/api/encargados/disponibles`

---

### 3.7. Consultar por Especialidad
**GET** `http://localhost:8080/api/encargados/especialidad/Catering`

---

### 3.8. Consultar Disponibles por Especialidad
**GET** `http://localhost:8080/api/encargados/disponibles/Catering`

---

## 🎉 4. SERVICIO DE EVENTOS

### 4.1. Crear Evento
**POST** `http://localhost:8080/api/eventos`

**Body (JSON):**
```json
{
    "nombre": "Boda de Ana y Luis",
    "descripcion": "Ceremonia y recepción",
    "fechaInicio": "2025-12-25T18:00:00",
    "fechaFin": "2025-12-26T02:00:00",
    "ubicacion": "Hotel Los Delfines, Lima",
    "presupuesto": 15000.00,
    "clienteId": 1,
    "planificacion": {
        "tareasPlanificadas": "Decoración, Catering, Música, Fotografía",
        "fechaPlanificacion": "2025-11-01T10:00:00",
        "recursos": "2 salones, equipo de sonido, pista de baile",
        "cronograma": "18:00 Ceremonia, 19:30 Cocktail, 21:00 Cena"
    }
}
```

**Respuesta Esperada:**
```json
{
    "success": true,
    "message": "Evento creado exitosamente",
    "data": {
        "eventoId": 1,
        "nombre": "Boda de Ana y Luis",
        "descripcion": "Ceremonia y recepción",
        "fechaInicio": "2025-12-25T18:00:00",
        "fechaFin": "2025-12-26T02:00:00",
        "ubicacion": "Hotel Los Delfines, Lima",
        "presupuesto": 15000.00,
        "estado": "PENDIENTE",
        "clienteId": 1,
        "clienteNombre": "Juan Pérez",
        "planificacion": {
            "planificacionId": 1,
            "tareasPlanificadas": "Decoración, Catering, Música, Fotografía",
            "fechaPlanificacion": "2025-11-01T10:00:00",
            "recursos": "2 salones, equipo de sonido, pista de baile",
            "cronograma": "18:00 Ceremonia, 19:30 Cocktail, 21:00 Cena"
        }
    }
}
```

---

### 4.2. Asignar Encargado a Evento
**PATCH** `http://localhost:8080/api/eventos/1/asignar`

**Body (JSON):**
```json
{
    "encargadoId": 1
}
```

---

### 4.3. Actualizar Estado del Evento
**PATCH** `http://localhost:8080/api/eventos/1/estado`

**Body (JSON):**
```json
{
    "nuevoEstado": "EN_EJECUCION"
}
```

**Estados válidos:** `PENDIENTE`, `EN_EJECUCION`, `FINALIZADO`, `CANCELADO`

---

### 4.4. Cerrar Evento
**POST** `http://localhost:8080/api/eventos/1/cerrar`

**Body (JSON):**
```json
{
    "resumen": "Evento exitoso, todos los servicios cumplidos",
    "costoFinal": 14500.00,
    "fechaCierre": "2025-12-26T03:00:00",
    "observaciones": "Cliente muy satisfecho, propina extra al personal"
}
```

---

### 4.5. Obtener Evento por ID
**GET** `http://localhost:8080/api/eventos/1`

---

### 4.6. Obtener Todos los Eventos
**GET** `http://localhost:8080/api/eventos`

---

### 4.7. Obtener Eventos de un Cliente
**GET** `http://localhost:8080/api/eventos/cliente/1`

---

### 4.8. Obtener Eventos de un Encargado
**GET** `http://localhost:8080/api/eventos/encargado/1`

---

### 4.9. Obtener Eventos por Estado
**GET** `http://localhost:8080/api/eventos/estado/PENDIENTE`

---

## 📊 5. SERVICIO DE SEGUIMIENTO

### 5.1. Crear Seguimiento
**POST** `http://localhost:8080/api/seguimientos`

**Body (JSON):**
```json
{
    "eventoId": 1,
    "estadoActual": "EN_PREPARACION",
    "observaciones": "Se inició la decoración del salón principal"
}
```

---

### 5.2. Agregar Evidencia
**PATCH** `http://localhost:8080/api/seguimientos/1/evidencia`

**Body (JSON):**
```json
{
    "evidenciaUrl": "https://drive.google.com/fotos/evento1/salon.jpg",
    "evidenciaPath": "/storage/eventos/1/evidencias/salon.jpg"
}
```

---

### 5.3. Obtener Seguimientos de un Evento
**GET** `http://localhost:8080/api/seguimientos/evento/1`

---

### 5.4. Obtener Último Seguimiento
**GET** `http://localhost:8080/api/seguimientos/evento/1/ultimo`

---

## 📧 6. SERVICIO DE NOTIFICACIONES

### 6.1. Enviar Correo
**POST** `http://localhost:8080/api/notificaciones/enviar`

**Body (JSON):**
```json
{
    "destinatario": "juan.perez@gmail.com",
    "asunto": "Confirmación de Evento",
    "mensaje": "Su evento ha sido confirmado para el 25 de diciembre.",
    "tipo": "CONFIRMACION"
}
```

**Tipos válidos:** `CONFIRMACION`, `RECORDATORIO`, `CAMBIO_ESTADO`, `CANCELACION`

---

## ⭐ 7. SERVICIO DE FEEDBACK (Comentarios)

### 7.1. Crear Comentario
**POST** `http://localhost:8080/api/comentarios`

**Body (JSON):**
```json
{
    "eventoId": 1,
    "clienteId": 1,
    "calificacion": 5,
    "opinion": "Excelente servicio, todo estuvo perfecto. Muy recomendado."
}
```

**Nota:** La calificación debe ser entre 1 y 5.

---

### 7.2. Obtener Comentarios de un Evento
**GET** `http://localhost:8080/api/comentarios/evento/1`

---

### 7.3. Obtener Comentarios de un Cliente
**GET** `http://localhost:8080/api/comentarios/cliente/1`

---

### 7.4. Obtener Feedback de un Encargado
**GET** `http://localhost:8080/api/comentarios/encargado/1`

---

### 7.5. Obtener Reporte de Satisfacción de un Encargado
**GET** `http://localhost:8080/api/comentarios/encargado/1/reporte`

**Respuesta Esperada:**
```json
{
    "success": true,
    "message": "Reporte obtenido exitosamente",
    "data": {
        "encargadoId": 1,
        "encargadoNombre": "Pedro Martínez",
        "especialidad": "Catering",
        "calificacionPromedio": 4.8,
        "totalComentarios": 5,
        "comentarios": [...]
    }
}
```

---

## 🔄 FLUJO COMPLETO DE PRUEBA

### Paso 1: Registrar Usuarios
```
1. POST /api/auth/registro (Cliente)
2. POST /api/auth/registro (Proveedor)
```

### Paso 2: Crear Perfiles
```
3. POST /api/clientes (Crear cliente)
4. POST /api/encargados (Crear encargado)
```

### Paso 3: Gestionar Evento
```
5. POST /api/eventos (Crear evento con planificación)
6. PATCH /api/eventos/1/asignar (Asignar encargado)
7. PATCH /api/eventos/1/estado (Cambiar a EN_EJECUCION)
```

### Paso 4: Hacer Seguimiento
```
8. POST /api/seguimientos (Crear seguimiento)
9. PATCH /api/seguimientos/1/evidencia (Agregar evidencia)
10. GET /api/seguimientos/evento/1 (Ver todos los seguimientos)
```

### Paso 5: Cerrar y Calificar
```
11. POST /api/eventos/1/cerrar (Cerrar evento)
12. PATCH /api/eventos/1/estado (Cambiar a FINALIZADO)
13. POST /api/comentarios (Dejar feedback)
14. GET /api/comentarios/encargado/1/reporte (Ver reporte)
```

---

## 🎯 CASOS DE PRUEBA RECOMENDADOS

### ✅ Validaciones que debes probar:

1. **Email duplicado**: Intenta registrar el mismo email dos veces
2. **Calificación inválida**: Envía calificación menor a 1 o mayor a 5
3. **Fecha pasada**: Intenta crear evento con fecha anterior a hoy
4. **Estado inválido**: Intenta cambiar a un estado que no existe
5. **Evento no finalizado**: Intenta crear comentario en evento no finalizado
6. **Encargado no disponible**: Intenta asignar encargado con disponible=false
7. **IDs inexistentes**: Usa IDs que no existen en la BD

---

## 📝 NOTAS IMPORTANTES

- ✅ **Sin autenticación**: Todos los endpoints son públicos por ahora
- ✅ **Sin tokens**: No necesitas enviar Bearer tokens
- ✅ **IDs automáticos**: Los IDs se generan automáticamente
- ✅ **Fechas**: Usa formato ISO 8601: `2025-12-25T18:00:00`
- ✅ **Decimales**: Usa punto para decimales: `15000.50`

---

## 🐛 TROUBLESHOOTING

### Error: "Usuario no encontrado"
- Verifica que el email esté registrado
- Usa GET /api/clientes para ver los clientes existentes

### Error: "Rol no encontrado"
- Asegúrate de que el proyecto haya iniciado correctamente
- Verifica en MySQL que existan las tablas roles y permisos

### Error: "Cannot connect to database"
- Verifica que MySQL esté corriendo
- Confirma que el schema eventos_peru_db exista
- Revisa las credenciales en application.properties

### Error: "Validation failed"
- Revisa que todos los campos obligatorios estén presentes
- Verifica el formato de fechas y emails
- Confirma que las calificaciones estén entre 1 y 5

---

## 🎉 ¡Listo para probar!

Empieza con el flujo de registro y ve avanzando paso a paso. ¡Mucho éxito! 🚀
