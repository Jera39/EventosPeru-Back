package com.eventosPeru.eventos.proveedor.model;

import com.eventosPeru.eventos.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "encargados")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Encargado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "encargado_id")
    private Long encargadoId;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especialidad;

    @Column(length = 500)
    private String experiencia;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "calificacion_promedio", precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal calificacionPromedio = BigDecimal.ZERO;

    @Column(name = "total_calificaciones")
    @Builder.Default
    private Integer totalCalificaciones = 0;

    @Column(name = "disponible")
    @Builder.Default
    private Boolean disponible = true;
}
