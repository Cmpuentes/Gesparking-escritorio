package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.dto.SalidaEscritorioDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoProjection;
import com.gesnnova.gserver.dto.TotalesSalidaDTO;
import com.gesnnova.gserver.model.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalidaEscritorioRepository extends JpaRepository<Salida, Integer> {

    @Query(value = "SELECT s.* " +
            "FROM salida s " +
            "INNER JOIN inicio_turno i ON i.numero_turno = s.turnosalida " +
            "WHERE i.estado = 'Activo' " +
            "ORDER BY s.idsalida DESC",
            nativeQuery = true)
    List<Salida> findSalidasTurnoActivo();

    @Query("SELECT new com.gesnnova.gserver.dto.TotalesSalidaDTO( " +
            "CAST(COALESCE(SUM(s.efectivo),0) AS integer), " +
            "CAST(COALESCE(SUM(s.tarjeta),0) AS integer), " +
            "CAST(COALESCE(SUM(s.transferencia),0) AS integer)) " +
            "FROM Salida s " +
            "JOIN InicioTurno i ON s.turnosalida = i.numeroTurno " +
            "WHERE i.estado = 'Activo'")
    TotalesSalidaDTO obtenerTotalesSalida();

    @Query("SELECT COALESCE(MAX(s.numfactura), 0) FROM Salida s")
    Integer obtenerMaxNumFactura();

    Optional<Salida> findTopByOrderByIdsalidaDesc();

    Optional<Salida> findTopByClienteAndTiposervicioOrderByIdsalidaDesc(String cliente, String tiposervicio);

    List<Salida> findByTiposervicioOrderByIdsalidaDesc(String tiposervicio);

    @Query("SELECT s FROM Salida s WHERE s.turno = :turno1 OR s.turno = :turno2 OR s.turno = :turno3")
    List<Salida> findByTurnos(@Param("turno1") String turno1,
                              @Param("turno2") String turno2,
                              @Param("turno3") String turno3);

    List<Salida> findByPlacaContainingOrderByIdsalidaDesc(String placa);

    List<Salida> findAllByOrderByIdsalidaDesc();

    Optional<Salida> findByNumfactura(int numfactura);

    //Para calcular los totales de los medios de pago usado en Jfinturnop
    @Query(value = "SELECT " +
            "COALESCE(SUM(t.efectivo), 0) AS totalEfectivo, " +
            "COALESCE(SUM(t.tarjeta), 0) AS totalTarjeta, " +
            "COALESCE(SUM(t.transferencia), 0) AS totalTransferencia " +
            "FROM ( " +
            "    SELECT s.efectivo, s.tarjeta, s.transferencia " +
            "    FROM salida s " +
            "    JOIN ingreso i ON i.idingreso = s.idingreso " +
            "    WHERE s.turnosalida = :numeroTurno AND i.tiposervicio = 'GENERAL' " +
            "    UNION ALL " +
            "    SELECT a.efectivo, a.tarjeta, a.transferencia " +
            "    FROM abonos a " +
            "    WHERE a.numero_turno = :numeroTurno " +
            ") t",
            nativeQuery = true)
    TotalesMediosPagoProjection obtenerTotalesMedioPago(@Param("numeroTurno") int numeroTurno);

}
