package com.eventosPeru.eventos.evento.service;

import com.eventosPeru.eventos.auth.model.Usuario;
import com.eventosPeru.eventos.auth.service.AuthService;
import com.eventosPeru.eventos.cliente.model.Cliente;
import com.eventosPeru.eventos.cliente.repository.ClienteRepository;
import com.eventosPeru.eventos.common.exception.BadRequestException;
import com.eventosPeru.eventos.common.exception.ResourceNotFoundException;
import com.eventosPeru.eventos.evento.dto.AsignacionRequest;
import com.eventosPeru.eventos.evento.dto.EstadoEventoRequest;
import com.eventosPeru.eventos.evento.dto.EventoRequest;
import com.eventosPeru.eventos.evento.dto.EventoResponse;
import com.eventosPeru.eventos.evento.model.Cierre;
import com.eventosPeru.eventos.evento.model.Evento;
import com.eventosPeru.eventos.evento.model.Planificacion;
import com.eventosPeru.eventos.evento.repository.CierreRepository;
import com.eventosPeru.eventos.evento.repository.EventoRepository;
import com.eventosPeru.eventos.evento.repository.PlanificacionRepository;
import com.eventosPeru.eventos.proveedor.model.Encargado;
import com.eventosPeru.eventos.proveedor.service.EncargadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final PlanificacionRepository planificacionRepository;
    private final CierreRepository cierreRepository;
    private final ClienteRepository clienteRepository;
    private final EncargadoService encargadoService;
    private final AuthService authService;

    @Transactional
    public EventoResponse crearEvento(EventoRequest request) {
        // Validar fechas
        if (request.getFechaFin().isBefore(request.getFechaInicio())) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        // Obtener cliente
        Cliente cliente;
        if (request.getClienteId() != null) {
            // Admin creando evento para un cliente específico
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        } else {
            // Cliente creando su propio evento
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Usuario usuario = authService.obtenerUsuarioPorEmail(email);
            cliente = clienteRepository.findByUsuarioId(usuario.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado para el usuario actual"));
        }

        // Crear evento
        Evento evento = Evento.builder()
                .cliente(cliente)
                .titulo(request.getNombre())
                .descripcion(request.getDescripcion())
                .ubicacion(request.getUbicacion())
                .presupuesto(request.getPresupuesto())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .estado("PENDIENTE")
                .build();

        Evento eventoGuardado = eventoRepository.save(evento);

        // Crear planificación si se proporciona
        if (request.getPlanificacion() != null) {
            Planificacion planificacion = Planificacion.builder()
                    .evento(eventoGuardado)
                    .detalle(request.getPlanificacion().getTareasPlanificadas())
                    .build();
            planificacionRepository.save(planificacion);
        }

        return mapearAEventoResponse(eventoGuardado);
    }

    @Transactional
    public EventoResponse asignarEncargado(Long eventoId, AsignacionRequest request) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));

        Encargado encargado = encargadoService.obtenerEncargadoPorId(request.getEncargadoId());

        if (!encargado.getDisponible()) {
            throw new BadRequestException("El encargado no está disponible");
        }

        evento.setEncargado(encargado);
        Evento eventoActualizado = eventoRepository.save(evento);

        return mapearAEventoResponse(eventoActualizado);
    }

    @Transactional
    public EventoResponse actualizarEstado(Long eventoId, EstadoEventoRequest request) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));

        String nuevoEstado = request.getEstado().toUpperCase();
        
        // Validar transiciones de estado
        if (!esTransicionValida(evento.getEstado(), nuevoEstado)) {
            throw new BadRequestException("Transición de estado no válida de " + 
                    evento.getEstado() + " a " + nuevoEstado);
        }

        evento.setEstado(nuevoEstado);
        Evento eventoActualizado = eventoRepository.save(evento);

        return mapearAEventoResponse(eventoActualizado);
    }

    @Transactional
    public EventoResponse cerrarEvento(Long eventoId, String resumen) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));

        if (!"FINALIZADO".equals(evento.getEstado())) {
            evento.setEstado("FINALIZADO");
            eventoRepository.save(evento);
        }

        // Crear o actualizar cierre
        Cierre cierre = cierreRepository.findByEventoEventoId(eventoId)
                .orElse(Cierre.builder()
                        .evento(evento)
                        .build());

        cierre.setResumen(resumen);
        cierre.setFechaCierre(LocalDateTime.now());
        cierreRepository.save(cierre);

        return mapearAEventoResponse(evento);
    }

    public EventoResponse obtenerEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));

        return mapearAEventoResponse(evento);
    }

    public List<EventoResponse> listarEventosPorCliente(Long clienteId) {
        return eventoRepository.findByClienteClienteId(clienteId).stream()
                .map(this::mapearAEventoResponse)
                .toList();
    }

    public List<EventoResponse> listarEventosPorEncargado(Long encargadoId) {
        return eventoRepository.findByEncargadoEncargadoId(encargadoId).stream()
                .map(this::mapearAEventoResponse)
                .toList();
    }

    public List<EventoResponse> listarEventosPorEstado(String estado) {
        return eventoRepository.findByEstado(estado.toUpperCase()).stream()
                .map(this::mapearAEventoResponse)
                .toList();
    }

    public List<EventoResponse> listarTodosLosEventos() {
        return eventoRepository.findAll().stream()
                .map(this::mapearAEventoResponse)
                .toList();
    }

    private boolean esTransicionValida(String estadoActual, String nuevoEstado) {
        return switch (estadoActual) {
            case "PENDIENTE" -> "EN_EJECUCION".equals(nuevoEstado) || "CANCELADO".equals(nuevoEstado);
            case "EN_EJECUCION" -> "FINALIZADO".equals(nuevoEstado) || "CANCELADO".equals(nuevoEstado);
            case "FINALIZADO", "CANCELADO" -> false;
            default -> false;
        };
    }

    private EventoResponse mapearAEventoResponse(Evento evento) {
        return EventoResponse.builder()
                .eventoId(evento.getEventoId())
                .clienteId(evento.getCliente() != null ? evento.getCliente().getClienteId() : null)
                .clienteNombre(evento.getCliente() != null ? evento.getCliente().getNombre() : null)
                .encargadoId(evento.getEncargado() != null ? evento.getEncargado().getEncargadoId() : null)
                .encargadoNombre(evento.getEncargado() != null ? evento.getEncargado().getNombre() : null)
                .titulo(evento.getTitulo())
                .descripcion(evento.getDescripcion())
                .estado(evento.getEstado())
                .ubicacion(evento.getUbicacion())
                .presupuesto(evento.getPresupuesto())
                .fechaInicio(evento.getFechaInicio())
                .fechaFin(evento.getFechaFin())
                .fechaCreacion(evento.getFechaCreacion())
                .build();
    }
}
