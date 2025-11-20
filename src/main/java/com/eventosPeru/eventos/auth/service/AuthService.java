package com.eventosPeru.eventos.auth.service;

import com.eventosPeru.eventos.auth.dto.*;
import com.eventosPeru.eventos.auth.model.Rol;
import com.eventosPeru.eventos.auth.model.Usuario;
import com.eventosPeru.eventos.auth.repository.RolRepository;
import com.eventosPeru.eventos.auth.repository.UsuarioRepository;
import com.eventosPeru.eventos.cliente.model.Cliente;
import com.eventosPeru.eventos.cliente.repository.ClienteRepository;
import com.eventosPeru.eventos.common.exception.BadRequestException;
import com.eventosPeru.eventos.common.exception.ResourceNotFoundException;
import com.eventosPeru.eventos.proveedor.model.Encargado;
import com.eventosPeru.eventos.proveedor.repository.EncargadoRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ClienteRepository clienteRepository;
    private final EncargadoRepository encargadoRepository;

    public AuthService(UsuarioRepository usuarioRepository, 
                       RolRepository rolRepository, 
                       PasswordEncoder passwordEncoder,
                       @Lazy AuthenticationManager authenticationManager,
                       ClienteRepository clienteRepository,
                       EncargadoRepository encargadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.clienteRepository = clienteRepository;
        this.encargadoRepository = encargadoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
    }

    @Transactional
    public AuthResponse registrar(RegistroRequest request) {
        // Validar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        // Buscar el rol
        Rol rol = rolRepository.findByNombre("ROLE_" + request.getRol().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + request.getRol()));

        // Crear el usuario
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .roles(roles)
                .activo(true)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Variables para los IDs de perfiles
        Long clienteId = null;
        Long encargadoId = null;

        // Crear perfil según el rol
        String rolNombre = request.getRol().toUpperCase();
        if ("CLIENTE".equals(rolNombre)) {
            Cliente cliente = Cliente.builder()
                    .usuario(usuarioGuardado)
                    .nombre(usuarioGuardado.getNombre())
                    .email(usuarioGuardado.getEmail())
                    .telefono(usuarioGuardado.getTelefono())
                    .direccion(null) // Se puede completar después en el perfil
                    .passwordHash(usuarioGuardado.getPasswordHash()) // Usar la misma contraseña hasheada
                    .build();
            Cliente clienteGuardado = clienteRepository.save(cliente);
            clienteId = clienteGuardado.getClienteId();
        } else if ("PROVEEDOR".equals(rolNombre)) {
            Encargado encargado = Encargado.builder()
                    .usuario(usuarioGuardado)
                    .nombre(usuarioGuardado.getNombre())
                    .telefono(usuarioGuardado.getTelefono())
                    .especialidad("General") // Valor por defecto
                    .experiencia("0 años") // Valor por defecto
                    .disponible(true)
                    .build();
            Encargado encargadoGuardado = encargadoRepository.save(encargado);
            encargadoId = encargadoGuardado.getEncargadoId();
        }

        return AuthResponse.builder()
                .id(usuarioGuardado.getId())
                .email(usuarioGuardado.getEmail())
                .nombre(usuarioGuardado.getNombre())
                .telefono(usuarioGuardado.getTelefono())
                .roles(usuarioGuardado.getRoles().stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toSet()))
                .clienteId(clienteId)
                .encargadoId(encargadoId)
                .mensaje("Usuario registrado exitosamente")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        // Autenticar
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Cargar usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Obtener IDs de perfiles si existen
        Long clienteId = null;
        Long encargadoId = null;

        // Buscar perfiles
        var clienteOpt = clienteRepository.findByUsuarioId(usuario.getId());
        if (clienteOpt.isPresent()) {
            clienteId = clienteOpt.get().getClienteId();
        }

        var encargadoOpt = encargadoRepository.findByUsuarioId(usuario.getId());
        if (encargadoOpt.isPresent()) {
            encargadoId = encargadoOpt.get().getEncargadoId();
        }

        return AuthResponse.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .telefono(usuario.getTelefono())
                .roles(usuario.getRoles().stream()
                        .map(Rol::getNombre)
                        .collect(Collectors.toSet()))
                .clienteId(clienteId)
                .encargadoId(encargadoId)
                .mensaje("Login exitoso")
                .build();
    }

    @Transactional
    public String solicitarRecuperacionPassword(RecuperarPasswordRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + request.getEmail()));

        // Por ahora solo retornamos el email como confirmación
        // En el futuro se puede implementar el envío de email
        return "Se enviará un correo a: " + usuario.getEmail();
    }

    @Transactional
    public void resetearPassword(ResetPasswordRequest request) {
        // Buscar usuario por email directamente (sin token)
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Actualizar contraseña
        usuario.setPasswordHash(passwordEncoder.encode(request.getNuevaPassword()));
        usuarioRepository.save(usuario);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }
}
