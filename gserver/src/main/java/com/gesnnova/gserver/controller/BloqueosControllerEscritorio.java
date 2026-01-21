package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.BloqueosDTO;
import com.gesnnova.gserver.model.Bloqueos;
import com.gesnnova.gserver.service.BloqueoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloqueos/escritorio")
@CrossOrigin(origins = "*")
public class BloqueosControllerEscritorio {

    @Autowired
    private BloqueoEscritorioService bloqueoEscritorioService;

    @GetMapping("/verificar/{codigo}")
    public ResponseEntity<Boolean> estaBloqueado(@PathVariable String codigo) {
        return ResponseEntity.ok(bloqueoEscritorioService.estaBloqueado(codigo));
    }

    @GetMapping("/administrativos")
    public ResponseEntity<List<Bloqueos>> listar(@RequestParam(required = false) String codigo) {
        List<Bloqueos> bloqueos = bloqueoEscritorioService.listarBloqueos(codigo);
        return ResponseEntity.ok(bloqueos);
    }

    //VAMOS POR AQUÍ
    @PostMapping("/insertar/administrativo")
    public ResponseEntity<Bloqueos> insertar(@RequestBody BloqueosDTO dto) {
        Bloqueos nuevo = bloqueoEscritorioService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/editar/administrativo/{id}")
    public ResponseEntity<?> editar(@PathVariable Integer id, @RequestBody BloqueosDTO dto) {
        try {
            Bloqueos actualizado = bloqueoEscritorioService.editar(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    //Eliminar bloqueos en modulo administrativo
    @DeleteMapping("/eliminar/administrativo/{id}")
    public ResponseEntity<?> eliminarBloqueos(@PathVariable Integer id) {
        boolean eliminado = bloqueoEscritorioService.eliminarBloqueos(id);
        if (eliminado) {
            return ResponseEntity.ok().body("{\"message\":\"Eliminado correctamente\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Registro no encontrado\"}");
        }
    }
}
