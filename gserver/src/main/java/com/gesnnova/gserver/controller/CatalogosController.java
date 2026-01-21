package com.gesnnova.gserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@CrossOrigin(origins = "*")
public class CatalogosController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/tipos-vehiculo")
    public ResponseEntity<List<String>> obtenerTiposVehiculo() {

        String sql = "SELECT DISTINCT tipovehiculo FROM tarifas ORDER BY tipovehiculo";

        List<String> tipos = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("tipovehiculo")
        );

        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/tipos-servicio")
    public ResponseEntity<List<String>> obtenerTiposServicio() {

        String sql = "SELECT DISTINCT tiposervicio FROM tarifas ORDER BY tiposervicio";

        List<String> servicios = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getString("tiposervicio")
        );

        return ResponseEntity.ok(servicios);
    }
}
