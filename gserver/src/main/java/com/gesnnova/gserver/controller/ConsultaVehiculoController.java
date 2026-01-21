package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.ConsultaVehiculoResult;
import com.gesnnova.gserver.service.ConsultaVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consulta")
public class ConsultaVehiculoController {

    @Autowired
    private ConsultaVehiculoService consultaVehiculoService;

    @GetMapping("/{placa}")
    public ResponseEntity<ConsultaVehiculoResult> consultarVehiculo(@PathVariable String placa) {
        ConsultaVehiculoResult result = consultaVehiculoService.consultarVehiculo(placa.trim());

        // ✅ Siempre devolver HTTP 200 OK, incluso si success = false
        return ResponseEntity.ok(result);
    }
}
