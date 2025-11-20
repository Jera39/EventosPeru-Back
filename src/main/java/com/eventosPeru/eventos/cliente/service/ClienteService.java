package com.eventosPeru.eventos.cliente.service;

import com.eventosPeru.eventos.auth.model.Usuario;
import com.eventosPeru.eventos.auth.service.AuthService;
import com.eventosPeru.eventos.cliente.dto.ClienteRequest;
import com.eventosPeru.eventos.cliente.dto.ClienteResponse;
import com.eventosPeru.eventos.cliente.dto.HistorialEventoResponse;
import com.eventosPeru.eventos.cliente.model.Cliente;
import com.eventosPeru.eventos.cliente.repository.ClienteRepository;
import com.eventosPeru.eventos.common.exception.BadRequestException;
import com.eventosPeru.eventos.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AuthService authService;

    @Transactional
    public ClienteResponse crearCliente(ClienteRequest request) {
        // Validar si el email ya existe
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        // Buscar si existe un usuario con ese email
        Usuario usuario = null;
        try {
            usuario = authService.obtenerUsuarioPorEmail(request.getEmail());
        } catch (ResourceNotFoundException e) {
            // Si no existe usuario, lo creamos como parte del cliente
            // Por ahora solo creamos el cliente sin usuario asociado
        }

        // Crear el cliente
        Cliente cliente = Cliente.builder()
                .usuario(usuario)
                .nombre(request.getNombre())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .passwordHash(request.getPassword()) // Para creación directa se mantiene
                .build();

        Cliente clienteGuardado = clienteRepository.save(cliente);

        return mapearAClienteResponse(clienteGuardado);
    }

    // Método interno para crear cliente cuando ya tenemos el usuario (desde AuthService)
    @Transactional
    public Cliente crearClienteConUsuario(Usuario usuario) {
        return Cliente.builder()
                .usuario(usuario)
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .direccion(null) // Se puede actualizar después
                .passwordHash(usuario.getPasswordHash()) // Usar la misma contraseña hasheada
                .build();
    }

    @Transactional
    public ClienteResponse actualizarCliente(Long clienteId, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));

        // Validar si el email ya existe y no pertenece al cliente actual
        if (!cliente.getEmail().equals(request.getEmail()) && 
            clienteRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }

        cliente.setNombre(request.getNombre());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        Cliente clienteActualizado = clienteRepository.save(cliente);

        return mapearAClienteResponse(clienteActualizado);
    }

    public ClienteResponse obtenerPerfil(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));

        return mapearAClienteResponse(cliente);
    }

    public ClienteResponse obtenerPerfilActual() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = authService.obtenerUsuarioPorEmail(email);
        
        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado para el usuario actual"));

        return mapearAClienteResponse(cliente);
    }

    public ClienteResponse obtenerPerfilPorUsuarioId(Long usuarioId) {
        Cliente cliente = clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado para el usuario con ID: " + usuarioId));

        return mapearAClienteResponse(cliente);
    }

    public List<HistorialEventoResponse> obtenerHistorialEventos(Long clienteId) {
        // Validar que el cliente existe
        clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));

        // Por ahora retornamos lista vacía, se implementará cuando tengamos el modelo Evento
        return new ArrayList<>();
    }

    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::mapearAClienteResponse)
                .toList();
    }

    public Cliente obtenerClientePorId(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + clienteId));
    }

    private ClienteResponse mapearAClienteResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .clienteId(cliente.getClienteId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
    }
}
