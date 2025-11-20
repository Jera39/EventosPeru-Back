package com.eventosPeru.eventos.feedback.service;

import com.eventosPeru.eventos.auth.model.Usuario;
import com.eventosPeru.eventos.auth.service.AuthService;
import com.eventosPeru.eventos.cliente.model.Cliente;
import com.eventosPeru.eventos.cliente.repository.ClienteRepository;
import com.eventosPeru.eventos.common.exception.BadRequestException;
import com.eventosPeru.eventos.common.exception.ResourceNotFoundException;
import com.eventosPeru.eventos.evento.model.Evento;
import com.eventosPeru.eventos.evento.repository.EventoRepository;
import com.eventosPeru.eventos.feedback.dto.ComentarioRequest;
import com.eventosPeru.eventos.feedback.dto.ComentarioResponse;
import com.eventosPeru.eventos.feedback.dto.ReporteSatisfaccionResponse;
import com.eventosPeru.eventos.feedback.model.Comentario;
import com.eventosPeru.eventos.feedback.repository.ComentarioRepository;
import com.eventosPeru.eventos.proveedor.model.Encargado;
import com.eventosPeru.eventos.proveedor.service.EncargadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final EventoRepository eventoRepository;
    private final ClienteRepository clienteRepository;
    private final AuthService authService;
    private final EncargadoService encargadoService;

    @Transactional
    public ComentarioResponse crearComentario(ComentarioRequest request) {
        // Obtener el evento
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + request.getEventoId()));

        // Validar que el evento esté finalizado
        if (!"FINALIZADO".equals(evento.getEstado())) {
            throw new BadRequestException("Solo puedes calificar eventos finalizados");
        }

        // Obtener el cliente actual
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = authService.obtenerUsuarioPorEmail(email);
        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado para el usuario actual"));

        // Validar que el cliente sea el dueño del evento
        if (!evento.getCliente().getClienteId().equals(cliente.getClienteId())) {
            throw new BadRequestException("Solo puedes calificar tus propios eventos");
        }

        // Crear comentario
        Comentario comentario = Comentario.builder()
                .evento(evento)
                .cliente(cliente)
                .calificacion(request.getCalificacion())
                .opinion(request.getOpinion())
                .build();

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        // Actualizar calificación del encargado si existe
        if (evento.getEncargado() != null) {
            encargadoService.actualizarCalificacion(
                    evento.getEncargado().getEncargadoId(),
                    request.getCalificacion()
            );
        }

        return mapearAComentarioResponse(comentarioGuardado);
    }

    public List<ComentarioResponse> obtenerComentariosPorEvento(Long eventoId) {
        return comentarioRepository.findByEventoEventoId(eventoId).stream()
                .map(this::mapearAComentarioResponse)
                .toList();
    }

    public List<ComentarioResponse> obtenerComentariosPorCliente(Long clienteId) {
        return comentarioRepository.findByClienteClienteId(clienteId).stream()
                .map(this::mapearAComentarioResponse)
                .toList();
    }

    public List<ComentarioResponse> obtenerFeedbackPorEncargado(Long encargadoId) {
        return comentarioRepository.findByEncargadoId(encargadoId).stream()
                .map(this::mapearAComentarioResponse)
                .toList();
    }

    public ReporteSatisfaccionResponse generarReporteSatisfaccion(Long encargadoId) {
        Encargado encargado = encargadoService.obtenerEncargadoPorId(encargadoId);
        
        List<ComentarioResponse> comentarios = obtenerFeedbackPorEncargado(encargadoId);
        
        Double promedioCalculado = comentarioRepository.calcularPromedioCalificacionPorEncargado(encargadoId);
        if (promedioCalculado == null) {
            promedioCalculado = 0.0;
        }

        return ReporteSatisfaccionResponse.builder()
                .encargadoId(encargado.getEncargadoId())
                .encargadoNombre(encargado.getNombre())
                .calificacionPromedio(promedioCalculado)
                .totalComentarios(comentarios.size())
                .comentarios(comentarios)
                .build();
    }

    private ComentarioResponse mapearAComentarioResponse(Comentario comentario) {
        return ComentarioResponse.builder()
                .comentarioId(comentario.getComentarioId())
                .eventoId(comentario.getEvento().getEventoId())
                .eventoTitulo(comentario.getEvento().getTitulo())
                .clienteId(comentario.getCliente().getClienteId())
                .clienteNombre(comentario.getCliente().getNombre())
                .calificacion(comentario.getCalificacion())
                .opinion(comentario.getOpinion())
                .fecha(comentario.getFecha())
                .build();
    }
}
