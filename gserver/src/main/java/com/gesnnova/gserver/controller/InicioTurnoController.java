package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.InicioTurnoResponseDTO;
import com.gesnnova.gserver.dto.InicioTurnoUpdate;
import com.gesnnova.gserver.model.InicioTurno;
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
@CrossOrigin(origins = "*")
public class InicioTurnoController {

    @Autowired
    private InicioTurnoService inicioTurnoService;

    // ===============================
    // OBTENER ESTADO DE TURNO
    // ===============================
    @GetMapping("/estado/{numeroTurno}")
    public ResponseEntity<Map<String, Object>> obtenerEstadoTurno(
            @PathVariable int numeroTurno,
            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa
    ) {
        Map<String, Object> response = new HashMap<>();

        String estado =
                inicioTurnoService.obtenerEstadoTurno(numeroTurno, idEmpresa);

        if (estado == null) {
            response.put("success", false);
            response.put("message", "Turno no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.put("success", true);
        response.put("estado", estado);
        return ResponseEntity.ok(response);
    }

    // ===============================
    // ÚLTIMOS TURNOS POR EMPRESA
    // ===============================
//    @GetMapping("/ultimos")
//    public ResponseEntity<List<String>> obtenerUltimosTurnos(
//            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa
//    ) {
//        return ResponseEntity.ok(
//                inicioTurnoService.obtenerUltimosTurnos(idEmpresa)
//        );
//    }

    // ===============================
    // CONSULTA DE TURNO
    // ===============================

    @GetMapping("/consulta/{numeroTurno}")
    public ResponseEntity<InicioTurnoResponseDTO> realizarConsulta(
            @PathVariable int numeroTurno,
            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa
    ) {
        return inicioTurnoService
                .realizarConsulta(numeroTurno, idEmpresa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // ===============================
    // LISTAR TURNOS (ADMIN)
    // ===============================
    @GetMapping("/listar")
    public List<InicioTurno> listar(
            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa
    ) {
        return inicioTurnoService.listarPorEmpresa(idEmpresa);
    }

    // ===============================
    // OBTENER TURNO POR ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(
            @PathVariable Integer id,
            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa
    ) {
        InicioTurno turno =
                inicioTurnoService.obtenerPorId(id, idEmpresa);

        if (turno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Turno no encontrado");
        }

        return ResponseEntity.ok(turno);
    }

    // ===============================
    // ACTUALIZAR TURNO
    // ===============================
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa,
            @RequestBody InicioTurnoUpdate dto
    ) {
        InicioTurno actualizado =
                inicioTurnoService.actualizar(id, idEmpresa, dto);

        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo actualizar: Turno no encontrado");
        }

        return ResponseEntity.ok(actualizado);
    }

    // ===============================
    // ELIMINAR TURNO (LÓGICO)
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Integer id,
            @RequestHeader("X-ID-EMPRESA") Integer idEmpresa
    ) {
        boolean eliminado =
                inicioTurnoService.eliminarLogico(id, idEmpresa);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar: Turno no encontrado");
        }

        return ResponseEntity.ok("Turno eliminado correctamente");
    }
}
