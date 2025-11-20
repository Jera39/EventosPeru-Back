package com.eventosPeru.eventos.seguimiento.controller;

import com.eventosPeru.eventos.common.dto.ApiResponse;
import com.eventosPeru.eventos.seguimiento.dto.SeguimientoRequest;
import com.eventosPeru.eventos.seguimiento.dto.SeguimientoResponse;
import com.eventosPeru.eventos.seguimiento.service.SeguimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<SeguimientoResponse>> crearSeguimiento(
            @Valid @RequestBody SeguimientoRequest request) {
        SeguimientoResponse response = seguimientoService.crearSeguimiento(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Seguimiento creado exitosamente", response));
    }

    @PatchMapping("/{seguimientoId}/evidencia")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<SeguimientoResponse>> agregarEvidencia(
            @PathVariable Long seguimientoId,
            @RequestBody String evidencia) {
        SeguimientoResponse response = seguimientoService.agregarEvidencia(seguimientoId, evidencia);
        return ResponseEntity.ok(ApiResponse.success("Evidencia agregada exitosamente", response));
    }

    @GetMapping("/evento/{eventoId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<SeguimientoResponse>>> obtenerSeguimientosPorEvento(
            @PathVariable Long eventoId) {
        List<SeguimientoResponse> seguimientos = seguimientoService.obtenerSeguimientosPorEvento(eventoId);
        return ResponseEntity.ok(ApiResponse.success("Seguimientos obtenidos exitosamente", seguimientos));
    }

    @GetMapping("/evento/{eventoId}/ultimo")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<SeguimientoResponse>> obtenerUltimoSeguimiento(
            @PathVariable Long eventoId) {
        SeguimientoResponse response = seguimientoService.obtenerUltimoSeguimiento(eventoId);
        return ResponseEntity.ok(ApiResponse.success("Último seguimiento obtenido exitosamente", response));
    }

    @GetMapping("/{seguimientoId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<SeguimientoResponse>> obtenerSeguimiento(@PathVariable Long seguimientoId) {
        SeguimientoResponse response = seguimientoService.obtenerSeguimiento(seguimientoId);
        return ResponseEntity.ok(ApiResponse.success("Seguimiento obtenido exitosamente", response));
    }
}
