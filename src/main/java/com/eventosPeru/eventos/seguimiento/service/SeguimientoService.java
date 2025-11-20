package com.eventosPeru.eventos.seguimiento.service;

import com.eventosPeru.eventos.common.exception.ResourceNotFoundException;
import com.eventosPeru.eventos.evento.model.Evento;
import com.eventosPeru.eventos.evento.repository.EventoRepository;
import com.eventosPeru.eventos.seguimiento.dto.SeguimientoRequest;
import com.eventosPeru.eventos.seguimiento.dto.SeguimientoResponse;
import com.eventosPeru.eventos.seguimiento.model.Seguimiento;
import com.eventosPeru.eventos.seguimiento.repository.SeguimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final EventoRepository eventoRepository;

    @Transactional
    public SeguimientoResponse crearSeguimiento(SeguimientoRequest request) {
        Evento evento = eventoRepository.findById(request.getEventoId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + request.getEventoId()));

        Seguimiento seguimiento = Seguimiento.builder()
                .evento(evento)
                .estadoActual(request.getEstadoActual())
                .observaciones(request.getObservaciones())
                .build();

        Seguimiento seguimientoGuardado = seguimientoRepository.save(seguimiento);

        return mapearASeguimientoResponse(seguimientoGuardado);
    }

    @Transactional
    public SeguimientoResponse agregarEvidencia(Long seguimientoId, String evidencia) {
        Seguimiento seguimiento = seguimientoRepository.findById(seguimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Seguimiento no encontrado con ID: " + seguimientoId));

        seguimiento.setEvidencia(evidencia);
        Seguimiento seguimientoActualizado = seguimientoRepository.save(seguimiento);

        return mapearASeguimientoResponse(seguimientoActualizado);
    }

    public List<SeguimientoResponse> obtenerSeguimientosPorEvento(Long eventoId) {
        return seguimientoRepository.findByEventoEventoIdOrderByFechaDesc(eventoId).stream()
                .map(this::mapearASeguimientoResponse)
                .toList();
    }

    public SeguimientoResponse obtenerUltimoSeguimiento(Long eventoId) {
        List<Seguimiento> seguimientos = seguimientoRepository.findByEventoEventoIdOrderByFechaDesc(eventoId);
        
        if (seguimientos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron seguimientos para el evento con ID: " + eventoId);
        }

        return mapearASeguimientoResponse(seguimientos.get(0));
    }

    public SeguimientoResponse obtenerSeguimiento(Long seguimientoId) {
        Seguimiento seguimiento = seguimientoRepository.findById(seguimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Seguimiento no encontrado con ID: " + seguimientoId));

        return mapearASeguimientoResponse(seguimiento);
    }

    private SeguimientoResponse mapearASeguimientoResponse(Seguimiento seguimiento) {
        return SeguimientoResponse.builder()
                .seguimientoId(seguimiento.getSeguimientoId())
                .eventoId(seguimiento.getEvento().getEventoId())
                .eventoTitulo(seguimiento.getEvento().getTitulo())
                .estadoActual(seguimiento.getEstadoActual())
                .observaciones(seguimiento.getObservaciones())
                .evidencia(seguimiento.getEvidencia())
                .fecha(seguimiento.getFecha())
                .build();
    }
}
