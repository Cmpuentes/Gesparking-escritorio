package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.HistorialTurnoResult;
import com.gesnnova.gserver.dto.SalidaResponseDTO;
import com.gesnnova.gserver.service.HistorialTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/turno")
public class HistorialTurnoController {

    @Autowired
    private HistorialTurnoService historialTurnoService;

    @GetMapping("/historial/{numeroTurno}")
    public ResponseEntity<HistorialTurnoResult> obtenerHistorial(@PathVariable int numeroTurno) {
        HistorialTurnoResult result = historialTurnoService.obtenerHistorialPorTurno(numeroTurno);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/historial/salida/{idSalida}")
    public ResponseEntity<?> obtenerDetalleSalida(@PathVariable int idSalida) {
        Optional<SalidaResponseDTO> detalle = historialTurnoService.obtenerDetalleSalida(idSalida);

        if (detalle.isPresent()) {
            return ResponseEntity.ok(detalle.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró información para la salida con id " + idSalida);
        }
    }

}
