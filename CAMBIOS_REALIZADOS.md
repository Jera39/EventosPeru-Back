# CAMBIOS REALIZADOS - Eliminación de JWT y Migración a MySQL

## 1. Base de Datos - Migración a MySQL
- **Antes**: PostgreSQL en puerto 5432
- **Ahora**: MySQL en puerto 3306
  - Host: 127.0.0.1
  - Username: root
  - Password: $Uni18390327@$
  - Database/Schema: eventos_peru_db

## 2. Eliminación Completa de JWT
### Archivos Eliminados:
- JwtService.java - Servicio de generación y validación de tokens
- JwtAuthenticationFilter.java - Filtro de autenticación JWT
- TokenResponse.java - DTO de respuesta con token

### Dependencias Eliminadas del pom.xml:
- io.jsonwebtoken:jjwt-api:0.12.3
- io.jsonwebtoken:jjwt-impl:0.12.3
- io.jsonwebtoken:jjwt-jackson:0.12.3

### Archivos Modificados:
- **Usuario.java**: Eliminados campos esetToken y esetTokenExpiry
- **UsuarioRepository.java**: Eliminados métodos indByResetToken y indByResetTokenAndResetTokenExpiryAfter
- **SecurityConfig.java**: 
  - Eliminada inyección de JwtAuthenticationFilter
  - Cambiado SessionCreationPolicy a IF_REQUIRED (en lugar de STATELESS)
  - Todos los endpoints /api/** ahora permitAll() (sin autenticación)
- **AuthService.java**: 
  - Eliminada inyección de JwtService
  - Métodos egistrar() y login() ahora retornan AuthResponse en lugar de TokenResponse
  - Simplificados métodos de recuperación de password (sin tokens)
- **AuthController.java**: Actualizado para usar AuthResponse

### Nuevo DTO Creado:
- **AuthResponse.java**: Reemplaza TokenResponse, contiene:
  - id, email, nombre, telefono, roles, mensaje

## 3. Configuración de MySQL
### pom.xml:
`xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
`

### application.properties:
`properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/eventos_peru_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=$Uni18390327@$
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
`

## 4. Seguridad Simplificada
- **Sin tokens**: No se requiere autenticación por token
- **Todos los endpoints públicos**: Por ahora todos los endpoints están abiertos
- **Autenticación básica**: Solo validación de usuario/password en login
- **Sesiones**: Ahora usa sesiones HTTP estándar (SessionCreationPolicy.IF_REQUIRED)

## 5. Próximos Pasos
1. Ejecutar el proyecto con: mvnw spring-boot:run
2. Las tablas se crearán automáticamente en MySQL
3. Se inicializarán los roles: ROLE_CLIENTE, ROLE_PROVEEDOR, ROLE_ADMIN
4. Los endpoints están disponibles en: http://localhost:8080/api/

## Notas Importantes:
- Ya no hay Bearer tokens en las respuestas
- Los DTOs de recuperación de password ahora usan email directamente
- La seguridad está simplificada (todos los endpoints públicos por ahora)
