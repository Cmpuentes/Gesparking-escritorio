package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoServicioRepository extends JpaRepository<TipoServicio, Integer> {

    Optional<TipoServicio> findByNombre(String nombre);
}
