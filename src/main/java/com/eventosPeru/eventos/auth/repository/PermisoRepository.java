package com.eventosPeru.eventos.auth.repository;

import com.eventosPeru.eventos.auth.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
