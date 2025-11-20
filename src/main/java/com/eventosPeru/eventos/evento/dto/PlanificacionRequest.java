package com.eventosPeru.eventos.evento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanificacionRequest {

    @NotBlank(message = "Las tareas planificadas son obligatorias")
    private String tareasPlanificadas;

    private LocalDateTime fechaPlanificacion;

    private String recursos;

    private String cronograma;
}
