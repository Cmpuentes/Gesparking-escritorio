package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.AuditoriaIngreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaIngresoRepository extends JpaRepository<AuditoriaIngreso, Integer> {
}
