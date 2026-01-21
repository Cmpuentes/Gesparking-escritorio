package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.ResumenTurnoDTO;
import com.gesnnova.gserver.service.ResumenTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turno")
public class ResumenTurnoController {

    @Autowired
    private ResumenTurnoService resumenTurnoService;

    @GetMapping("/resumen")
    public ResponseEntity<ResumenTurnoDTO> obtenerResumen(@RequestParam("turno") int turno) {
        ResumenTurnoDTO resumen = resumenTurnoService.obtenerTotalesPorTurno(turno);
        return ResponseEntity.ok(resumen);
    }
}