package com.eventosPeru.eventos.seguimiento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoResponse {
    private Long seguimientoId;
    private Long eventoId;
    private String eventoTitulo;
    private String estadoActual;
    private String observaciones;
    private String evidencia;
    private LocalDateTime fecha;
}
