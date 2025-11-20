package com.eventosPeru.eventos.evento.model;

import com.eventosPeru.eventos.proveedor.model.Encargado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ejecuciones")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ejecucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ejecucion_id")
    private Long ejecucionId;

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "evento_id")
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "encargado_id", referencedColumnName = "encargado_id")
    private Encargado encargado;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
