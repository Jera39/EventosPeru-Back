package com.eventosPeru.eventos.evento.repository;

import com.eventosPeru.eventos.evento.model.Planificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanificacionRepository extends JpaRepository<Planificacion, Long> {
    Optional<Planificacion> findByEventoEventoId(Long eventoId);
}
