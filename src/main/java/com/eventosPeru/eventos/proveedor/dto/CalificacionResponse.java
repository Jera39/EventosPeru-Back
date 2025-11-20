package com.eventosPeru.eventos.proveedor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalificacionResponse {
    private Long encargadoId;
    private String nombre;
    private BigDecimal calificacionPromedio;
    private Integer totalCalificaciones;
}
