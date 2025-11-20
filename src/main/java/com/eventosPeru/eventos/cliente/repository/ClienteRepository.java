package com.eventosPeru.eventos.cliente.repository;

import com.eventosPeru.eventos.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByUsuarioId(Long usuarioId);
    boolean existsByEmail(String email);
}
