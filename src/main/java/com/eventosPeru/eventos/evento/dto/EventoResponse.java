package com.eventosPeru.eventos.evento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponse {
    private Long eventoId;
    private Long clienteId;
    private String clienteNombre;
    private Long encargadoId;
    private String encargadoNombre;
    private String titulo;
    private String descripcion;
    private String estado;
    private String ubicacion;
    private Double presupuesto;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private LocalDateTime fechaCreacion;
}
