package com.eventosPeru.eventos.seguimiento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoRequest {

    @NotNull(message = "El ID del evento es obligatorio")
    private Long eventoId;

    @NotBlank(message = "El estado actual es obligatorio")
    private String estadoActual;

    private String observaciones;
}
