package com.eventosPeru.eventos.feedback.repository;

import com.eventosPeru.eventos.feedback.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByEventoEventoId(Long eventoId);
    List<Comentario> findByClienteClienteId(Long clienteId);
    
    @Query("SELECT c FROM Comentario c WHERE c.evento.encargado.encargadoId = :encargadoId")
    List<Comentario> findByEncargadoId(Long encargadoId);
    
    @Query("SELECT AVG(c.calificacion) FROM Comentario c WHERE c.evento.encargado.encargadoId = :encargadoId")
    Double calcularPromedioCalificacionPorEncargado(Long encargadoId);
}
