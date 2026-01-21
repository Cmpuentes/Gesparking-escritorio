package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.CierrePrintDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cierre")
public class CierreConsultaController {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(CierreConsultaController.class);

    @Autowired
    public CierreConsultaController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/ultimoPorTurno")
    public ResponseEntity<?> obtenerUltimoCierrePorTurno(@RequestParam("turno") int numeroTurno) {
        String sql = "SELECT turno, numero_turno, empleado, fechaingreso, fechasalida, " +
                "total_vehiculos, efectivo, tarjeta, transferencia, otros_ingresos, total_abonos, " +
                "efectivo_liquido, total_recaudado, observaciones " +
                "FROM salida_turno WHERE numero_turno = ? ORDER BY idfinturno DESC LIMIT 1";

        try {
            CierrePrintDTO cierre = jdbcTemplate.queryForObject(sql, new Object[]{numeroTurno}, (rs, rowNum) -> {
                CierrePrintDTO c = new CierrePrintDTO();
                c.setTurno(rs.getString("turno"));
                c.setNumeroturno(rs.getInt("numero_turno"));
                c.setEmpleado(rs.getString("empleado"));
                c.setFechaingreso(rs.getString("fechaingreso"));
                c.setFechasalida(rs.getString("fechasalida"));
                c.setTotalvehiculos(rs.getInt("total_vehiculos"));
                c.setEfectivo(rs.getInt("efectivo"));
                c.setTarjeta(rs.getInt("tarjeta"));
                c.setTransferencia(rs.getInt("transferencia"));
                c.setOtrosingresos(rs.getInt("otros_ingresos"));
                c.setTotalabonos(rs.getInt("total_abonos"));
                c.setEfectivoliquido(rs.getInt("efectivo_liquido"));
                c.setTotalrecaudado(rs.getInt("total_recaudado"));
                c.setObservaciones(rs.getString("observaciones"));
                return c;
            });

            return ResponseEntity.ok(cierre);
        } catch (EmptyResultDataAccessException e) {
            // No hay cierre para ese turno
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "No hay cierres para el turno " + numeroTurno);
            return ResponseEntity.status(404).body(resp);
        } catch (Exception e) {
            logger.error("Error al consultar cierre por turno {}", numeroTurno, e);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "Error del servidor");
            return ResponseEntity.status(500).body(resp);
        }
    }

    // Opcional: también puedes exponer el último cierre global sin parámetro
    @GetMapping("/ultimo")
    public ResponseEntity<?> obtenerUltimoCierreGlobal() {
        String sql = "SELECT turno, numero_turno, empleado, fechaingreso, fechasalida, " +
                "total_vehiculos, efectivo, tarjeta, transferencia, otros_ingresos, total_abonos, " +
                "efectivo_liquido, total_recaudado, observaciones " +
                "FROM salida_turno ORDER BY idfinturno DESC LIMIT 1";

        try {
            CierrePrintDTO cierre = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                CierrePrintDTO c = new CierrePrintDTO();
                c.setTurno(rs.getString("turno"));
                c.setNumeroturno(rs.getInt("numero_turno"));
                c.setEmpleado(rs.getString("empleado"));
                c.setFechaingreso(rs.getString("fechaingreso"));
                c.setFechasalida(rs.getString("fechasalida"));
                c.setTotalvehiculos(rs.getInt("total_vehiculos"));
                c.setEfectivo(rs.getInt("efectivo"));
                c.setTarjeta(rs.getInt("tarjeta"));
                c.setTransferencia(rs.getInt("transferencia"));
                c.setOtrosingresos(rs.getInt("otros_ingresos"));
                c.setTotalabonos(rs.getInt("total_abonos"));
                c.setEfectivoliquido(rs.getInt("efectivo_liquido"));
                c.setTotalrecaudado(rs.getInt("total_recaudado"));
                c.setObservaciones(rs.getString("observaciones"));
                return c;
            });

            return ResponseEntity.ok(cierre);
        } catch (EmptyResultDataAccessException e) {
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "No hay cierres registrados");
            return ResponseEntity.status(404).body(resp);
        } catch (Exception e) {
            logger.error("Error al consultar último cierre", e);
            Map<String, Object> resp = new HashMap<>();
            resp.put("success", false);
            resp.put("message", "Error del servidor");
            return ResponseEntity.status(500).body(resp);
        }
    }

}
