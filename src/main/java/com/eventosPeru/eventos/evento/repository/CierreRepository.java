package com.eventosPeru.eventos.evento.repository;

import com.eventosPeru.eventos.evento.model.Cierre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CierreRepository extends JpaRepository<Cierre, Long> {
    Optional<Cierre> findByEventoEventoId(Long eventoId);
}
