package com.eventosPeru.eventos.feedback.controller;

import com.eventosPeru.eventos.common.dto.ApiResponse;
import com.eventosPeru.eventos.feedback.dto.ComentarioRequest;
import com.eventosPeru.eventos.feedback.dto.ComentarioResponse;
import com.eventosPeru.eventos.feedback.dto.ReporteSatisfaccionResponse;
import com.eventosPeru.eventos.feedback.service.ComentarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ApiResponse<ComentarioResponse>> crearComentario(
            @Valid @RequestBody ComentarioRequest request) {
        ComentarioResponse response = comentarioService.crearComentario(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Comentario creado exitosamente", response));
    }

    @GetMapping("/evento/{eventoId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<ComentarioResponse>>> obtenerComentariosPorEvento(
            @PathVariable Long eventoId) {
        List<ComentarioResponse> comentarios = comentarioService.obtenerComentariosPorEvento(eventoId);
        return ResponseEntity.ok(ApiResponse.success("Comentarios obtenidos exitosamente", comentarios));
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<ComentarioResponse>>> obtenerComentariosPorCliente(
            @PathVariable Long clienteId) {
        List<ComentarioResponse> comentarios = comentarioService.obtenerComentariosPorCliente(clienteId);
        return ResponseEntity.ok(ApiResponse.success("Comentarios del cliente obtenidos exitosamente", comentarios));
    }

    @GetMapping("/encargado/{encargadoId}")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<ComentarioResponse>>> obtenerFeedbackPorEncargado(
            @PathVariable Long encargadoId) {
        List<ComentarioResponse> feedback = comentarioService.obtenerFeedbackPorEncargado(encargadoId);
        return ResponseEntity.ok(ApiResponse.success("Feedback del encargado obtenido exitosamente", feedback));
    }

    @GetMapping("/encargado/{encargadoId}/reporte")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<ReporteSatisfaccionResponse>> generarReporteSatisfaccion(
            @PathVariable Long encargadoId) {
        ReporteSatisfaccionResponse reporte = comentarioService.generarReporteSatisfaccion(encargadoId);
        return ResponseEntity.ok(ApiResponse.success("Reporte de satisfacción generado exitosamente", reporte));
    }
}
