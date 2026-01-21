package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Bloqueos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloqueoEscritorioRepository  extends JpaRepository<Bloqueos, Integer> {

    Optional<Bloqueos> findByCodigo(String codigo);

    List<Bloqueos> findByCodigoContainingIgnoreCase(String codigo);
}
