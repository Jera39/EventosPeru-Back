package com.eventosPeru.eventos.notificacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponse {
    private String destinatario;
    private String asunto;
    private String estado; // ENVIADO, ERROR
    private LocalDateTime fechaEnvio;
    private String mensaje;
}
