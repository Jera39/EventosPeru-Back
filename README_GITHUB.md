# 🎉 Sistema de Gestión de Eventos - EventosPeru

Sistema integral de gestión de eventos desarrollado con **Spring Boot** que permite la administración completa de eventos, clientes, proveedores y servicios relacionados.

## 📋 Descripción del Proyecto

EventosPeru es una plataforma backend REST API que facilita la organización y gestión de eventos, conectando clientes con proveedores de servicios y permitiendo un seguimiento completo del ciclo de vida de cada evento.

## 🚀 Características Principales

- ✅ **Gestión de Clientes**: Registro y administración de perfiles de clientes
- ✅ **Gestión de Proveedores**: Control de proveedores de servicios para eventos
- ✅ **Administración de Eventos**: Creación, actualización y seguimiento de eventos
- ✅ **Sistema de Feedback**: Valoraciones y comentarios sobre servicios
- ✅ **Seguimiento en Tiempo Real**: Monitoreo del estado de eventos y servicios
- ✅ **Notificaciones**: Sistema de notificaciones para usuarios
- ✅ **Autenticación y Autorización**: Seguridad con JWT y roles de usuario
- ✅ **Envío de Emails**: Notificaciones por correo electrónico

## 🛠️ Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **MySQL** - Base de datos relacional
- **Lombok** - Reducción de código boilerplate
- **Bean Validation** - Validación de datos
- **JavaMailSender** - Envío de correos
- **Maven** - Gestión de dependencias

## 📁 Estructura del Proyecto

```
src/main/java/com/eventosPeru/eventos/
├── auth/              # Autenticación y autorización
├── cliente/           # Gestión de clientes
├── evento/            # Gestión de eventos
├── proveedor/         # Gestión de proveedores
├── feedback/          # Sistema de valoraciones
├── seguimiento/       # Seguimiento de eventos
├── notificacion/      # Sistema de notificaciones
├── common/            # Clases comunes y utilidades
└── config/            # Configuraciones de Spring
```

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura en capas** basada en el patrón **MVC**:

- **Controller**: Maneja las peticiones HTTP y respuestas
- **Service**: Contiene la lógica de negocio
- **Repository**: Acceso a datos con Spring Data JPA
- **Model**: Entidades de base de datos
- **DTO**: Objetos de transferencia de datos

## 🔧 Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+

### Variables de Entorno / Configuración

Configurar en `application.properties`:

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/eventos_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

# Email (opcional)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_contraseña_app
```

### Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/eventos-peru.git
cd eventos-peru
```

2. Configurar la base de datos en `application.properties`

3. Compilar el proyecto:
```bash
mvn clean install
```

4. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📡 Endpoints Principales

### Autenticación
- `POST /api/auth/register` - Registrar nuevo usuario
- `POST /api/auth/login` - Iniciar sesión

### Clientes
- `GET /api/clientes` - Listar todos los clientes
- `GET /api/clientes/{id}` - Obtener cliente por ID
- `POST /api/clientes` - Crear nuevo cliente
- `PUT /api/clientes/{id}` - Actualizar cliente
- `GET /api/clientes/perfil` - Obtener perfil actual

### Eventos
- `GET /api/eventos` - Listar eventos
- `GET /api/eventos/{id}` - Obtener evento por ID
- `POST /api/eventos` - Crear nuevo evento
- `PUT /api/eventos/{id}` - Actualizar evento
- `DELETE /api/eventos/{id}` - Eliminar evento

### Proveedores
- `GET /api/proveedores` - Listar proveedores
- `GET /api/proveedores/{id}` - Obtener proveedor por ID
- `POST /api/proveedores` - Crear proveedor
- `PUT /api/proveedores/{id}` - Actualizar proveedor

*(Para documentación completa de endpoints, consultar `GUIA_POSTMAN.md`)*

## 🔐 Seguridad

El sistema implementa autenticación basada en **JWT** con los siguientes roles:

- **ADMIN**: Acceso completo al sistema
- **CLIENTE**: Acceso a funcionalidades de cliente
- **PROVEEDOR**: Acceso a funcionalidades de proveedor

## 📚 Documentación Adicional

- [Guía de Uso](GUIA_DE_USO.md)
- [Guía de Postman](GUIA_POSTMAN.md)
- [Resumen del Proyecto](RESUMEN_PROYECTO.md)
- [Cambios Realizados](CAMBIOS_REALIZADOS.md)

## 👥 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add: AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto fue desarrollado con fines académicos para el curso de **Arquitectura Orientada a Servicios** - UTP.

## 👨‍💻 Autor

Desarrollado como proyecto académico - Universidad Tecnológica del Perú

## 📞 Contacto

Para consultas sobre el proyecto, por favor contactar a través del repositorio de GitHub.

---

⭐ Si este proyecto te fue útil, no olvides darle una estrella en GitHub!
