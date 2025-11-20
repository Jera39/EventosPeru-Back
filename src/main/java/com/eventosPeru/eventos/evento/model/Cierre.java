package com.eventosPeru.eventos.evento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cierres")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cierre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cierre_id")
    private Long cierreId;

    @OneToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "evento_id")
    private Evento evento;

    @Column(length = 2000)
    private String resumen;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @PrePersist
    protected void onCreate() {
        if (fechaCierre == null) {
            fechaCierre = LocalDateTime.now();
        }
    }
}
