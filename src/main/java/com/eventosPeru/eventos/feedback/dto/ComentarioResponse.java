package com.eventosPeru.eventos.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioResponse {
    private Long comentarioId;
    private Long eventoId;
    private String eventoTitulo;
    private Long clienteId;
    private String clienteNombre;
    private Integer calificacion;
    private String opinion;
    private LocalDateTime fecha;
}
