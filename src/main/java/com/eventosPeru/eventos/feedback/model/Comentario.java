package com.eventosPeru.eventos.feedback.model;

import com.eventosPeru.eventos.cliente.model.Cliente;
import com.eventosPeru.eventos.evento.model.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    private Long comentarioId;

    @ManyToOne
    @JoinColumn(name = "evento_id", referencedColumnName = "evento_id")
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private Integer calificacion; // 1-5

    @Column(length = 1000)
    private String opinion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
