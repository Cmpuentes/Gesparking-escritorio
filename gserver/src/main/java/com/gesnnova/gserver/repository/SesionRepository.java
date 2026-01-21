package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Sesiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SesionRepository extends JpaRepository<Sesiones, Integer> {
    Optional<Sesiones> findByIdempleado(Integer idempleado);
    Optional<Sesiones> findByToken(String token);

    void deleteByIdempleado(Integer idempleado);
}
