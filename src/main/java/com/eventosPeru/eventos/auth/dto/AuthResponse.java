package com.eventosPeru.eventos.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String email;
    private String nombre;
    private String telefono;
    private Set<String> roles;
    private String mensaje;
    private Long clienteId;
    private Long encargadoId;
}
