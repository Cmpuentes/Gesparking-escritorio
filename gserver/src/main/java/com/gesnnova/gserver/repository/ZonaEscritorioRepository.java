package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaEscritorioRepository extends JpaRepository<Zona, Integer> {

    List<Zona> findByNumeroContainingIgnoreCaseOrderByIdzona(String numero);
}
