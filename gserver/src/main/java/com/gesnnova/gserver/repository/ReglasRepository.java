package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Reglas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReglasRepository extends JpaRepository<Reglas, Integer> {

    List<Reglas> findByIdempresa(Integer idempresa);

    Optional<Reglas> findByIdreglasAndIdempresa(Integer idreglas, Integer idempresa);

    Optional<Reglas> findByTiposervicioAndIdempresa(String tiposervicio, Integer idempresa);

    boolean existsByIdreglasAndIdempresa(Integer idreglas, Integer idempresa);
}


