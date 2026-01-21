package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.dto.TotalesMediosPagoAbonosDTO;
import com.gesnnova.gserver.model.Abonos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbonoEscritorioRepository extends JpaRepository<Abonos, Integer> {

    Optional<Abonos> findByCliente(String cliente);

    @Query("SELECT a FROM Abonos a WHERE a.cliente LIKE %:cliente% ORDER BY a.idabonos")
    List<Abonos> buscarPorCliente(@Param("cliente") String cliente);

    @Query("SELECT new com.gesnnova.gserver.dto.TotalesMediosPagoAbonosDTO(" +
            "COALESCE(SUM(a.efectivo),0), " +
            "COALESCE(SUM(a.tarjeta),0), " +
            "COALESCE(SUM(a.transferencia),0)) " +
            "FROM Abonos a " +
            "WHERE a.numeroturno = :numeroTurno")
    TotalesMediosPagoAbonosDTO obtenerTotalesPorTurno(@Param("numeroTurno") int numeroTurno);

}
