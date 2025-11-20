package com.eventosPeru.eventos.proveedor.repository;

import com.eventosPeru.eventos.proveedor.model.Encargado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncargadoRepository extends JpaRepository<Encargado, Long> {
    Optional<Encargado> findByUsuarioId(Long usuarioId);
    List<Encargado> findByDisponibleTrue();
    List<Encargado> findByEspecialidad(String especialidad);
    List<Encargado> findByEspecialidadAndDisponibleTrue(String especialidad);
}
