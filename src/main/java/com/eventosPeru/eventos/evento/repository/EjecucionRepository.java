package com.eventosPeru.eventos.evento.repository;

import com.eventosPeru.eventos.evento.model.Ejecucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjecucionRepository extends JpaRepository<Ejecucion, Long> {
    List<Ejecucion> findByEventoEventoId(Long eventoId);
    List<Ejecucion> findByEncargadoEncargadoId(Long encargadoId);
}
