package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.AuditoriaSalidaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auditoria-salida")
@CrossOrigin(origins = "*")
public class AuditoriaSalidaController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/listar")
    public List<AuditoriaSalidaDTO> listarAuditoriaSalida() {
        String sql = "SELECT * FROM vista_auditoria_salida ORDER BY id_auditoria DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AuditoriaSalidaDTO dto = new AuditoriaSalidaDTO();
            dto.setId_auditoria(rs.getInt("id_auditoria"));
            dto.setOperacion(rs.getString("operacion"));
            dto.setPlaca_anterior(rs.getString("placa_anterior"));
            dto.setPlaca_nueva(rs.getString("placa_nueva"));
            dto.setValor_anterior(rs.getString("valor_anterior"));
            dto.setValor_nuevo(rs.getString("valor_nuevo"));
            dto.setEfectivo_anterior(rs.getString("efectivo_anterior"));
            dto.setEfectivo_nuevo(rs.getString("efectivo_nuevo"));
            dto.setTarjeta_anterior(rs.getString("tarjeta_anterior"));
            dto.setTarjeta_nueva(rs.getString("tarjeta_nueva"));
            dto.setTransferencia_anterior(rs.getString("transferencia_anterior"));
            dto.setTransferencia_nueva(rs.getString("transferencia_nueva"));
            dto.setTotal_anterior(rs.getString("total_anterior"));
            dto.setTotal_nuevo(rs.getString("total_nuevo"));
            dto.setEmpleado_salida(rs.getString("empleado_salida"));
            dto.setUsuario_app(rs.getString("usuario_app"));
            dto.setFecha(rs.getString("fecha"));
            dto.setTurno(rs.getString("turno"));
            return dto;
        });
    }
}
