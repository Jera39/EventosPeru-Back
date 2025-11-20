package com.eventosPeru.eventos.notificacion.controller;

import com.eventosPeru.eventos.common.dto.ApiResponse;
import com.eventosPeru.eventos.notificacion.dto.CorreoRequest;
import com.eventosPeru.eventos.notificacion.dto.NotificacionResponse;
import com.eventosPeru.eventos.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PostMapping("/enviar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NotificacionResponse>> enviarCorreo(@Valid @RequestBody CorreoRequest request) {
        NotificacionResponse response = notificacionService.enviarCorreo(request);
        return ResponseEntity.ok(ApiResponse.success("Proceso de envío completado", response));
    }
}
