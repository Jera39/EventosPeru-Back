package com.eventosPeru.eventos.evento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoEventoRequest {

    @NotBlank(message = "El estado es obligatorio")
    private String estado; // PENDIENTE, EN_EJECUCION, FINALIZADO, CANCELADO
}
