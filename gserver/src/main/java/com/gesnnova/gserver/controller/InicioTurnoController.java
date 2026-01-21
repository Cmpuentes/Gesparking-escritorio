package com.gesnnova.gserver.controller;


import com.gesnnova.gserver.dto.InicioTurnoResponseDTO;
import com.gesnnova.gserver.dto.InicioTurnoUpdate;
import com.gesnnova.gserver.model.InicioTurno;
import com.gesnnova.gserver.repository.InicioTurnoRepository;
import com.gesnnova.gserver.service.InicioTurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/turno")
public class InicioTurnoController {

    @Autowired
    private InicioTurnoService inicioTurnoService;

    @Autowired
    private InicioTurnoRepository inicioTurnoRepository;

    @GetMapping("/estado/{numeroTurno}")
    public ResponseEntity<Map<String, Object>> obtenerEstadoTurno(@PathVariable int numeroTurno) {
        Map<String, Object> response = new HashMap<>();
        try {
            String estado = inicioTurnoService.obtenerEstadoTurno(numeroTurno);

            if (estado == null) {
                response.put("success", false);
                response.put("message", "Turno no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put("success", true);
            response.put("estado", estado);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<String>> obtenerUltimosTurnos() {
        List<String> turnos = inicioTurnoRepository.findUltimosTurnos();
        return ResponseEntity.ok(turnos);
    }

    @GetMapping("/consulta/{numeroTurno}")
    public ResponseEntity<InicioTurnoResponseDTO> realizarConsulta(@PathVariable int numeroTurno) {
        return inicioTurnoService.realizarConsulta(numeroTurno)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Controladores para el modulo de actualización y eliminado de turnos por parte de los administrativos
    @GetMapping("/listar")
    public List<InicioTurno> listar() {
        return inicioTurnoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        InicioTurno turno = inicioTurnoService.obtenerPorId(id);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Turno no encontrado");
        }

        return ResponseEntity.ok(turno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @RequestBody InicioTurnoUpdate dto) {

        InicioTurno actualizado = inicioTurnoService.actualizar(id, dto);

        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo actualizar: Turno no encontrado");
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        boolean eliminado = inicioTurnoService.eliminarLogico(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar: Turno no encontrado");
        }

        return ResponseEntity.ok("Turno eliminado correctamente");
    }

}
