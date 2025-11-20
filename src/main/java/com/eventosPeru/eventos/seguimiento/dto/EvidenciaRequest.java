package com.eventosPeru.eventos.seguimiento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaRequest {

    @NotNull(message = "El ID del seguimiento es obligatorio")
    private Long seguimientoId;

    private String evidencia;
}
