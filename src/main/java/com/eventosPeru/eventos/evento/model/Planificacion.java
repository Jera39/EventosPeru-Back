package com.eventosPeru.eventos.evento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "planificaciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planificacion_id")
    private Long planificacionId;

    @OneToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "evento_id")
    private Evento evento;

    @Column(length = 2000)
    private String detalle;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }
}
