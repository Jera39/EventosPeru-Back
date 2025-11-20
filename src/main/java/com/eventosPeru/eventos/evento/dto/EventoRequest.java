package com.eventosPeru.eventos.evento.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 5, max = 200, message = "El nombre debe tener entre 5 y 200 caracteres")
    private String nombre;

    @Size(max = 1000, message = "La descripción no debe exceder 1000 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotNull(message = "El presupuesto es obligatorio")
    private Double presupuesto;

    @NotNull(message = "El cliente ID es obligatorio")
    private Long clienteId;

    private PlanificacionRequest planificacion;
}
