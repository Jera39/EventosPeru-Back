package com.eventosPeru.eventos.evento.repository;

import com.eventosPeru.eventos.evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByClienteClienteId(Long clienteId);
    List<Evento> findByEncargadoEncargadoId(Long encargadoId);
    List<Evento> findByEstado(String estado);
}
