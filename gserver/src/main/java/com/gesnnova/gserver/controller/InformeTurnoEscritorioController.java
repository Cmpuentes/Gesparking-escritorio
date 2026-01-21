package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.InformeDetalleDTO;
import com.gesnnova.gserver.dto.InformeTotalesDTO;
import com.gesnnova.gserver.service.InformeTurnoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escritorio/informes")
@CrossOrigin(origins = "*")
public class InformeTurnoEscritorioController {

    @Autowired
    private InformeTurnoEscritorioService informeTurnoEscritorioService;

    // Detalle desde una fecha
    @GetMapping("/detalle-desde")
    public ResponseEntity<List<InformeDetalleDTO>> detalleDesde(@RequestParam String inicio) {
        return ResponseEntity.ok(informeTurnoEscritorioService.obtenerDetalleDesde(inicio));
    }

    // Detalle entre fechas
    @GetMapping("/detalle-entre")
    public ResponseEntity<List<InformeDetalleDTO>> detalleEntre(
            @RequestParam String inicio,
            @RequestParam String fin) {
        return ResponseEntity.ok(informeTurnoEscritorioService.obtenerDetalleEntre(inicio, fin));
    }

    // Totales desde una fecha
    @GetMapping("/totales-desde")
    public ResponseEntity<InformeTotalesDTO> totalesDesde(@RequestParam String inicio) {
        return ResponseEntity.ok(informeTurnoEscritorioService.obtenerTotalesDesde(inicio));
    }

    // Totales entre fechas
    @GetMapping("/totales-entre")
    public ResponseEntity<InformeTotalesDTO> totalesEntre(
            @RequestParam String inicio,
            @RequestParam String fin) {
        return ResponseEntity.ok(informeTurnoEscritorioService.obtenerTotalesEntre(inicio, fin));
    }
}
