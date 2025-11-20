package com.eventosPeru.eventos.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {
    private Long clienteId;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDateTime fechaRegistro;
}
