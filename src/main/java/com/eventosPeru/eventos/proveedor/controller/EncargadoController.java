package com.eventosPeru.eventos.proveedor.controller;

import com.eventosPeru.eventos.common.dto.ApiResponse;
import com.eventosPeru.eventos.proveedor.dto.CalificacionResponse;
import com.eventosPeru.eventos.proveedor.dto.DisponibilidadRequest;
import com.eventosPeru.eventos.proveedor.dto.EncargadoRequest;
import com.eventosPeru.eventos.proveedor.dto.EncargadoResponse;
import com.eventosPeru.eventos.proveedor.service.EncargadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encargados")
@RequiredArgsConstructor
public class EncargadoController {

    private final EncargadoService encargadoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<EncargadoResponse>> registrarEncargado(
            @Valid @RequestBody EncargadoRequest request) {
        EncargadoResponse response = encargadoService.registrarEncargado(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Encargado registrado exitosamente", response));
    }

    @PutMapping("/{encargadoId}")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<EncargadoResponse>> actualizarEncargado(
            @PathVariable Long encargadoId,
            @Valid @RequestBody EncargadoRequest request) {
        EncargadoResponse response = encargadoService.actualizarEncargado(encargadoId, request);
        return ResponseEntity.ok(ApiResponse.success("Encargado actualizado exitosamente", response));
    }

    @PatchMapping("/{encargadoId}/disponibilidad")
    @PreAuthorize("hasAnyRole('PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<EncargadoResponse>> actualizarDisponibilidad(
            @PathVariable Long encargadoId,
            @Valid @RequestBody DisponibilidadRequest request) {
        EncargadoResponse response = encargadoService.actualizarDisponibilidad(encargadoId, request.getDisponible());
        return ResponseEntity.ok(ApiResponse.success("Disponibilidad actualizada exitosamente", response));
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EncargadoResponse>>> consultarDisponibles() {
        List<EncargadoResponse> encargados = encargadoService.consultarDisponibles();
        return ResponseEntity.ok(ApiResponse.success("Encargados disponibles obtenidos exitosamente", encargados));
    }

    @GetMapping("/especialidad/{especialidad}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EncargadoResponse>>> consultarPorEspecialidad(
            @PathVariable String especialidad) {
        List<EncargadoResponse> encargados = encargadoService.consultarPorEspecialidad(especialidad);
        return ResponseEntity.ok(ApiResponse.success("Encargados por especialidad obtenidos exitosamente", encargados));
    }

    @GetMapping("/disponibles/especialidad/{especialidad}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<List<EncargadoResponse>>> consultarDisponiblesPorEspecialidad(
            @PathVariable String especialidad) {
        List<EncargadoResponse> encargados = encargadoService.consultarDisponiblesPorEspecialidad(especialidad);
        return ResponseEntity.ok(ApiResponse.success(
                "Encargados disponibles por especialidad obtenidos exitosamente", encargados));
    }

    @GetMapping("/{encargadoId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<EncargadoResponse>> obtenerEncargado(@PathVariable Long encargadoId) {
        EncargadoResponse response = encargadoService.obtenerEncargado(encargadoId);
        return ResponseEntity.ok(ApiResponse.success("Encargado obtenido exitosamente", response));
    }

    @GetMapping("/{encargadoId}/calificacion")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROVEEDOR', 'ADMIN')")
    public ResponseEntity<ApiResponse<CalificacionResponse>> obtenerCalificacion(@PathVariable Long encargadoId) {
        CalificacionResponse response = encargadoService.obtenerCalificacion(encargadoId);
        return ResponseEntity.ok(ApiResponse.success("Calificación obtenida exitosamente", response));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<EncargadoResponse>>> listarEncargados() {
        List<EncargadoResponse> encargados = encargadoService.listarEncargados();
        return ResponseEntity.ok(ApiResponse.success("Encargados listados exitosamente", encargados));
    }
}
