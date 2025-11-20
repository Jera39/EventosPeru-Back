package com.eventosPeru.eventos.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteSatisfaccionResponse {
    private Long encargadoId;
    private String encargadoNombre;
    private Double calificacionPromedio;
    private Integer totalComentarios;
    private List<ComentarioResponse> comentarios;
}
