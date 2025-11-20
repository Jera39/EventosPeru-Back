package com.eventosPeru.eventos.seguimiento.model;

import com.eventosPeru.eventos.evento.model.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "seguimientos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seguimiento_id")
    private Long seguimientoId;

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "evento_id")
    private Evento evento;

    @Column(name = "estado_actual", nullable = false)
    private String estadoActual;

    @Column(length = 1000)
    private String observaciones;

    @Column(length = 500)
    private String evidencia; // URL o ruta de archivo

    @Column(nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
