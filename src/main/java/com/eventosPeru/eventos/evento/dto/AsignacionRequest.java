package com.eventosPeru.eventos.evento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionRequest {

    @NotNull(message = "El ID del encargado es obligatorio")
    private Long encargadoId;
}
