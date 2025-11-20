package com.eventosPeru.eventos.proveedor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadRequest {
    
    @NotNull(message = "El estado de disponibilidad es obligatorio")
    private Boolean disponible;
}
