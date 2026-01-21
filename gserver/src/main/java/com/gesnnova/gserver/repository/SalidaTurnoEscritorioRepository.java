package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.SalidaTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalidaTurnoEscritorioRepository extends JpaRepository<SalidaTurno, Integer> {

    // Lista todo ordenado por id desc
    List<SalidaTurno> findAllByOrderByIdfinturnoDesc();

    // Lista filtrando por "turno" (contiene, ignore case) y ordenando por id desc
    List<SalidaTurno> findByTurnoContainingIgnoreCaseOrderByIdfinturnoDesc(String turno);

    // DETALLE: desde una fecha (sin fin)
    @Query(value = """
    SELECT 
      turno, numero_turno, empleado, fechaingreso, fechasalida,
      SUM(efectivo) AS efectivo,
      SUM(tarjeta) AS tarjeta,
      SUM(transferencia) AS transferencia,
      SUM(otros_ingresos) AS otros_ingresos,
      SUM(efectivo_liquido) AS efectivo_liquido,
      SUM(total_recaudado) AS total_recaudado
    FROM salida_turno
    WHERE DATE(STR_TO_DATE(fechasalida, '%d-%m-%Y %h:%i %p')) >= STR_TO_DATE(:inicio, '%d-%m-%Y')
    GROUP BY turno, numero_turno, empleado, fechaingreso, fechasalida
    ORDER BY STR_TO_DATE(fechasalida, '%d-%m-%Y %h:%i %p') DESC
""", nativeQuery = true)
    List<Object[]> detalleDesde(@Param("inicio") String inicio);

    // DETALLE: entre fechas (con fin)
    @Query(value = """
    SELECT 
      turno, numero_turno, empleado, fechaingreso, fechasalida,
      SUM(efectivo) AS efectivo,
      SUM(tarjeta) AS tarjeta,
      SUM(transferencia) AS transferencia,
      SUM(otros_ingresos) AS otros_ingresos,
      SUM(efectivo_liquido) AS efectivo_liquido,
      SUM(total_recaudado) AS total_recaudado
    FROM salida_turno
    WHERE DATE(STR_TO_DATE(fechasalida, '%d-%m-%Y %h:%i %p')) 
          BETWEEN STR_TO_DATE(:inicio, '%d-%m-%Y') AND STR_TO_DATE(:fin, '%d-%m-%Y')
    GROUP BY turno, numero_turno, empleado, fechaingreso, fechasalida
    ORDER BY STR_TO_DATE(fechasalida, '%d-%m-%Y %h:%i %p') DESC
""", nativeQuery = true)
    List<Object[]> detalleEntre(@Param("inicio") String inicio, @Param("fin") String fin);

    // TOTALES: desde una fecha
    @Query(value = """
    SELECT 
      SUM(efectivo) AS efectivo,
      SUM(tarjeta) AS tarjeta,
      SUM(transferencia) AS transferencia,
      SUM(otros_ingresos) AS otros_ingresos,
      SUM(efectivo_liquido) AS efectivo_liquido,
      SUM(total_recaudado) AS total_recaudado
    FROM salida_turno
    WHERE DATE(STR_TO_DATE(fechasalida, '%d-%m-%Y %h:%i %p')) >= STR_TO_DATE(:inicio, '%d-%m-%Y')
""", nativeQuery = true)
    List<Object[]> totalesDesde(@Param("inicio") String inicio);

    // TOTALES: entre fechas
    @Query(value = """
    SELECT 
      SUM(efectivo) AS efectivo,
      SUM(tarjeta) AS tarjeta,
      SUM(transferencia) AS transferencia,
      SUM(otros_ingresos) AS otros_ingresos,
      SUM(efectivo_liquido) AS efectivo_liquido,
      SUM(total_recaudado) AS total_recaudado
    FROM salida_turno
    WHERE DATE(STR_TO_DATE(fechasalida, '%d-%m-%Y %h:%i %p')) 
          BETWEEN STR_TO_DATE(:inicio, '%d-%m-%Y') AND STR_TO_DATE(:fin, '%d-%m-%Y')
""", nativeQuery = true)
    List<Object[]> totalesEntre(@Param("inicio") String inicio, @Param("fin") String fin);

    Optional<SalidaTurno> findByNumeroturno(String numeroturno);

    Optional<SalidaTurno> findTopByOrderByIdfinturnoDesc();

    // Consulta para crear el pdf de jasperReportStudio del crierre de turno
    @Query(value = """
    SELECT 
           turno,
           numero_turno AS numeroturno,
           empleado,
           fechaingreso,
           fechasalida,
           total_vehiculos as totalvehiculos,
           base,
           efectivo,
           tarjeta,
           transferencia,
           otros_ingresos as otrosingresos, 
           efectivo_liquido as efectivoliquido,
           total_recaudado as totalrecaudado,
           observaciones,
           total_abonos as totalabonos
    FROM salida_turno
    ORDER BY idfinturno DESC
    LIMIT 1
""", nativeQuery = true)
    List<Object[]> findUltimoCierreNative();

//Consulta para generar la copia del cierre de turno al seleccionar la tabla
    @Query(value = """
    SELECT 
           turno,
           numero_turno AS numeroturno,
           empleado,
           fechaingreso,
           fechasalida,
           total_vehiculos as totalvehiculos,
           base,
           efectivo,
           tarjeta,
           transferencia,
           otros_ingresos as otrosingresos, 
           efectivo_liquido as efectivoliquido,
           total_recaudado as totalrecaudado,
           observaciones,
           total_abonos as totalabonos
    FROM salida_turno
    where numero_turno = ?
    ORDER BY idfinturno DESC
    
""", nativeQuery = true)
    List<Object[]> CopiaCierreTurno(@Param("Nturno") String Nturno);
}
