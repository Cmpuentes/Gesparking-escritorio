package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Tarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TarifaEScritorioRepository extends JpaRepository<Tarifas, Integer> {

    Optional<Tarifas> findByTipovehiculoAndTiposervicioAndIdempresa(
            String tipovehiculo,
            String tiposervicio,
            Integer idempresa
    );

    List<Tarifas> findByIdempresaOrderByIdtarifas(Integer idempresa);

    List<Tarifas> findByTipovehiculoContainingIgnoreCaseAndIdempresaOrderByIdtarifas(
            String tipovehiculo,
            Integer idempresa
    );

    Optional<Tarifas> findByIdtarifasAndIdempresa(
            Integer idtarifas,
            Integer idempresa
    );
    @Query("""
           SELECT DISTINCT t.tiposervicio
           FROM Tarifas t
           WHERE t.idempresa = :idempresa
           ORDER BY t.tiposervicio
           """)
    List<String> findTiposServicioByEmpresa(@Param("idempresa") Integer idempresa);
}
