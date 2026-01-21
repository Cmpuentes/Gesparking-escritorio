package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.CierrePrintDTO;
import com.gesnnova.gserver.dto.CierreRegistroRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cierre")
public class CierreRegistroController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, Object>> registrarCierre(@RequestBody CierreRegistroRequest dto) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validar campos requeridos
            if (dto.getTurno() == null || dto.getEmpleado() == null || dto.getFechaIngreso() == null ||
                    dto.getFechaSalida() == null || dto.getObservaciones() == null) {
                response.put("success", false);
                response.put("message", "Todos los campos son requeridos");
                return ResponseEntity.badRequest().body(response);
            }

            // Insertar en salida_turno
            String sql = "INSERT INTO salida_turno (" +
                    "turno, numero_turno, empleado, fechaingreso, fechasalida, recibos, total_vehiculos, base, efectivo, " +
                    "tarjeta, transferencia, otros_ingresos, efectivo_liquido, total_recaudado, estado, observaciones, total_abonos, abono_efectivo, abono_tarjeta, abono_transferencia) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql,
                    dto.getTurno(),
                    dto.getNumeroTurno(),
                    dto.getEmpleado(),
                    dto.getFechaIngreso(),
                    dto.getFechaSalida(),
                    dto.getRecibidos(),
                    dto.getTotalVehiculos(),
                    dto.getBase(),
                    dto.getEfectivo(),
                    dto.getTarjeta(),
                    dto.getTransferencia(),
                    dto.getOtrosIngresos(),
                    dto.getEfectivoLiquido(),
                    dto.getTotalRecaudado(),
                    "Activo",
                    dto.getObservaciones(),
                    dto.getTotalAbonos(),
                    dto.getAbonoEfectivo(),
                    dto.getAbonoTarjeta(),
                    dto.getAbonoTransferencia()
            );

            // Actualizar estado del turno en inicio_turno
            jdbcTemplate.update("UPDATE inicio_turno SET estado = ? WHERE numero_turno = ?", "Finalizado", dto.getNumeroTurno());

            // Consultar el registro recién insertado
            String selectSql = "SELECT turno, numero_turno, empleado, fechaingreso, fechasalida, " +
                    "total_vehiculos, efectivo, tarjeta, transferencia, otros_ingresos, total_abonos, " +
                    "efectivo_liquido, total_recaudado, observaciones " +
                    "FROM salida_turno WHERE numero_turno = ? ORDER BY idfinturno DESC LIMIT 1";

            CierrePrintDTO cierre = jdbcTemplate.queryForObject(selectSql, new Object[]{dto.getNumeroTurno()},
                    (rs, rowNum) -> {
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

            // Construir respuesta
            response.put("success", true);
            response.put("message", "Cierre exitoso");
            response.put("cierre", cierre);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Error del servidor");
            return ResponseEntity.status(500).body(response);
        }
    }
}