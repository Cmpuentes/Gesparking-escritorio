package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.service.InicioTurnoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/escritorio/inicioturno")
public class InicioTurnoEscritorioController {

    @Autowired
    private InicioTurnoEscritorioService inicioTurnoEscritorioService;

    @PutMapping("/finalizar/{numeroTurno}")
    public ResponseEntity<Map<String, Object>> finalizarTurno(@PathVariable int numeroTurno) {
        boolean exito = inicioTurnoEscritorioService.finalizarTurno(numeroTurno);

        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("mensaje", "Turno finalizado correctamente");
            response.put("turnoActivo", false);
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "No se encontró turno activo con ese número");
            response.put("turnoActivo", true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
