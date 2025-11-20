package com.eventosPeru.eventos.evento.controller;

import com.eventosPeru.eventos.common.dto.ApiResponse;
import com.eventosPeru.eventos.evento.dto.AsignacionRequest;
import com.eventosPeru.eventos.evento.dto.EstadoEventoRequest;
import com.eventosPeru.eventos.evento.dto.EventoRequest;
import com.eventosPeru.eventos.evento.dto.EventoResponse;
import com.eventosPeru.eventos.evento.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<EventoResponse>> crearEvento(@Valid @RequestBody EventoRequest request) {
        EventoResponse response = eventoService.crearEvento(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Evento creado exitosamente", response));
    }

    @PatchMapping("/{eventoId}/asignar")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    public ResponseEntity<ApiResponse<EventoResponse>> asignarEncargado(
            @PathVariable Long eventoId,
            @Valid @RequestBody AsignacionRequest request) {
        EventoResponse response = eventoService.asignarEncargado(eventoId, request);
        return ResponseEntity.ok(ApiResponse.success("Encargado asignado exitosamente", response));
    }

    @PatchMapping("/{eventoId}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVEEDOR', 'CLIENTE')")
    public ResponseEntity<ApiResponse<EventoResponse>> actualizarEstado(
            @PathVariable Long eventoId,
            @Valid @RequestBody EstadoEventoRequest request) {
        EventoResponse response = eventoService.actualizarEstado(eventoId, request);
        return ResponseEntity.ok(ApiResponse.success("Estado actualizado exitosamente", response));
    }

    @PostMapping("/{eventoId}/cerrar")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROVEEDOR')")
    public ResponseEntity<ApiResponse<EventoResponse>> cerrarEvento(
            @PathVariable Long eventoId,
            @RequestBody String resumen) {
        EventoResponse response = eventoService.cerrarEvento(eventoId, resumen);
        return ResponseEntity.ok(ApiResponse.success("Evento cerrado exitosamente", response));
    }

    @GetMapping("/{eventoId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<EventoResponse>> obtenerEvento(@PathVariable Long eventoId) {
        EventoResponse response = eventoService.obtenerEvento(eventoId);
        return ResponseEntity.ok(ApiResponse.success("Evento obtenido exitosamente", response));
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EventoResponse>>> listarEventosPorCliente(@PathVariable Long clienteId) {
        List<EventoResponse> eventos = eventoService.listarEventosPorCliente(clienteId);
        return ResponseEntity.ok(ApiResponse.success("Eventos del cliente obtenidos exitosamente", eventos));
    }

    @GetMapping("/encargado/{encargadoId}")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EventoResponse>>> listarEventosPorEncargado(@PathVariable Long encargadoId) {
        List<EventoResponse> eventos = eventoService.listarEventosPorEncargado(encargadoId);
        return ResponseEntity.ok(ApiResponse.success("Eventos del encargado obtenidos exitosamente", eventos));
    }

    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EventoResponse>>> listarEventosPorEstado(@PathVariable String estado) {
        List<EventoResponse> eventos = eventoService.listarEventosPorEstado(estado);
        return ResponseEntity.ok(ApiResponse.success("Eventos por estado obtenidos exitosamente", eventos));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EventoResponse>>> listarTodosLosEventos() {
        List<EventoResponse> eventos = eventoService.listarTodosLosEventos();
        return ResponseEntity.ok(ApiResponse.success("Todos los eventos obtenidos exitosamente", eventos));
    }
}
