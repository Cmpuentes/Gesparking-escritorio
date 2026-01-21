package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.PrepagoResponseDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoAbonosDTO;
import com.gesnnova.gserver.model.Abonos;
import com.gesnnova.gserver.service.AbonoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escritorio/abonos")
@CrossOrigin(origins = "*")
public class AbonosEscritorioController {

    @Autowired
    private AbonoEscritorioService abonoEscritorioService;

    @GetMapping("/prepago/{cliente}")
    public ResponseEntity<PrepagoResponseDTO> obtenerPrepago(@PathVariable String cliente) {
        PrepagoResponseDTO response = abonoEscritorioService.procesarPrepago(cliente);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Abonos>> buscarAbonos(@RequestParam String cliente) {
        List<Abonos> resultado = abonoEscritorioService.buscarAbonosPorCliente(cliente);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<Abonos> crearAbono(@RequestBody Abonos abono) {
        Abonos nuevo = abonoEscritorioService.guardarAbono(abono);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Abonos> editarAbono(@PathVariable Integer id, @RequestBody Abonos abono) {
        Abonos actualizado = abonoEscritorioService.actualizarAbono(id, abono);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAbono(@PathVariable Integer id) {
        try {
            abonoEscritorioService.eliminarAbono(id);
            return ResponseEntity.ok("{\"message\": \"Abono eliminado correctamente\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/totales/{numeroTurno}")
    public ResponseEntity<TotalesMediosPagoAbonosDTO> obtenerTotalesPorTurno(@PathVariable int numeroTurno) {
        TotalesMediosPagoAbonosDTO totales = abonoEscritorioService.obtenerTotalesPorTurno(numeroTurno);
        return ResponseEntity.ok(totales);
    }
}
