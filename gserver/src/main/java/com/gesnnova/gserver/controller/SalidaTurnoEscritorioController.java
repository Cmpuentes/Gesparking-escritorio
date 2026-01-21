package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.ReportesPDF.SalidaTurnoReporteDTO;
import com.gesnnova.gserver.dto.SalidaTurnoDTO;
import com.gesnnova.gserver.dto.SalidaTurnoPrintDTO;
import com.gesnnova.gserver.dto.SalidaTurnoUpdateDTO;
import com.gesnnova.gserver.model.SalidaTurno;
import com.gesnnova.gserver.service.SalidaTurnoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salida_turno/escritorio")
public class SalidaTurnoEscritorioController {

    @Autowired
    private SalidaTurnoEscritorioService salidaTurnoEscritorioService;

    // GET /api/salida-turno           -> lista todo (orden desc)
    // GET /api/salida-turno?turno=N   -> filtra por "turno" (contiene, ignore case)
    @GetMapping
    public ResponseEntity<List<SalidaTurno>> listar(@RequestParam(required = false) String turno) {
        return ResponseEntity.ok(salidaTurnoEscritorioService.listar(turno));
    }

    @GetMapping("/numero/{numTurno}")
    public ResponseEntity<?> obtenerPorNumeroTurno(@PathVariable String numTurno) {
        return salidaTurnoEscritorioService.obtenerPorNumeroTurno(numTurno)
                .map(salida -> ResponseEntity.ok(mapToDTO(salida)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Usado en el modulo de administrativos
    private SalidaTurnoPrintDTO mapToDTO(SalidaTurno salida) {
        SalidaTurnoPrintDTO dto = new SalidaTurnoPrintDTO();
        dto.setTurno(salida.getTurno());
        dto.setNumeroturno(salida.getNumeroturno());
        dto.setEmpleado(salida.getEmpleado());
        dto.setFechaingreso(salida.getFechaingreso());
        dto.setFechasalida(salida.getFechasalida());
        dto.setTotalvehiculos(salida.getTotalvehiculos());
        dto.setEfectivo(salida.getEfectivo());
        dto.setTarjeta(salida.getTarjeta());
        dto.setTransferencia(salida.getTransferencia());
        dto.setAbonoEfectivo(salida.getAbonoEfectivo());
        dto.setAbonoTarjeta(salida.getAbonoTarjeta());
        dto.setAbonoTransferencia(salida.getAbonoTransferencia());
        dto.setOtrosingresos(salida.getOtrosingresos());
        dto.setEfectivoliquido(salida.getEfectivoliquido());
        dto.setTotalrecaudado(salida.getTotalrecaudado());
        dto.setObservaciones(salida.getObservaciones());
        dto.setTotalabonos(salida.getTotalabonos());
        return dto;
    }

    @PostMapping("/insertar")
    public ResponseEntity<SalidaTurno> insertarSalida(@RequestBody SalidaTurnoDTO dto) {
        SalidaTurno salida = salidaTurnoEscritorioService.guardarSalidaTurno(dto);
        return ResponseEntity.ok(salida);
    }

    @GetMapping("/cierre-turno/ultimo")
    public ResponseEntity<SalidaTurnoReporteDTO> obtenerUltimoCierre() {
        SalidaTurnoReporteDTO dto = salidaTurnoEscritorioService.obtenerUltimoCierre();
        if (dto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/copia-cierre/{Nturno}")
    public ResponseEntity<?> obtenerCopiaCierre(@PathVariable String Nturno) {

        List<SalidaTurnoReporteDTO> lista = salidaTurnoEscritorioService.obtenerCopiaCierreTurno(Nturno);

        if (lista.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("No existen registros para el turno: " + Nturno);
        }

        return ResponseEntity.ok(lista);
    }

    //Sección usada para las modificaciones de SalidaTurno en el modulo administrativo
    // GET /salida_turno/escritorio/listar
    @GetMapping("/listar")
    public ResponseEntity<List<SalidaTurno>> listar() {
        List<SalidaTurno> lista = salidaTurnoEscritorioService.listarOrdenadoPorNumeroDesc();
        return ResponseEntity.ok(lista);
    }

    // GET /salida_turno/escritorio/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        SalidaTurno s = salidaTurnoEscritorioService.obtenerPorId(id);
        if (s == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SalidaTurno no encontrado");
        }
        return ResponseEntity.ok(s);
    }

    // PUT /salida_turno/escritorio/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody SalidaTurnoUpdateDTO dto) {
        SalidaTurno actualizado = salidaTurnoEscritorioService.actualizar(id, dto);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar: registro no encontrado");
        }
        return ResponseEntity.ok(actualizado);
    }

    // DELETE lógico /salida_turno/escritorio/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLogico(@PathVariable Integer id) {
        boolean eliminado = salidaTurnoEscritorioService.eliminarLogico(id);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar: registro no encontrado");
        }
        return ResponseEntity.ok("SalidaTurno eliminado (estado cambiado a 'Eliminado')");
    }
}
