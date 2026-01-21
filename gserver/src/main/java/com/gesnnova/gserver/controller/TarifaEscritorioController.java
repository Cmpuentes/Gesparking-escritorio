package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.TarifasAdminResDTO;
import com.gesnnova.gserver.dto.TarifasDTO;
import com.gesnnova.gserver.model.Tarifas;
import com.gesnnova.gserver.service.TarifaEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas/escritorio")
public class TarifaEscritorioController {

    @Autowired
    private TarifaEscritorioService tarifaEscritorioService;
    @Autowired
    private TarifaEscritorioService tarifaService;

    // ===============================
    // OBTENER TARIFA (operativo)
    // ===============================
    @GetMapping
    public ResponseEntity<TarifasDTO> obtenerTarifa(
            @RequestParam String tipovehiculo,
            @RequestParam String tiposervicio,
            @RequestParam Integer idempresa
    ) {
        TarifasDTO dto = tarifaEscritorioService
                .obtenerTarifa(tipovehiculo, tiposervicio, idempresa);
        return ResponseEntity.ok(dto);
    }

    // ===============================
    // LISTAR TARIFAS (administrativos)
    // ===============================
    @GetMapping("/administrativos")
    public ResponseEntity<List<Tarifas>> listar(
            @RequestParam(required = false) String tipovehiculo,
            @RequestParam Integer idempresa
    ) {
        return ResponseEntity.ok(
                tarifaEscritorioService
                        .listarPorTipoVehiculo(tipovehiculo, idempresa)
        );
    }

    // ===============================
    // CREAR TARIFA
    // ===============================
    @PostMapping
    public ResponseEntity<Tarifas> crearTarifa(
            @RequestBody TarifasAdminResDTO dto,
            @RequestParam Integer idempresa
    ) {
        Tarifas nuevaTarifa =
                tarifaEscritorioService.guardarTarifa(dto, idempresa);
        return ResponseEntity.ok(nuevaTarifa);
    }

    // ===============================
    // EDITAR TARIFA
    // ===============================
    @PutMapping("/{idtarifas}")
    public ResponseEntity<Tarifas> editar(
            @PathVariable Integer idtarifas,
            @RequestBody TarifasAdminResDTO dto,
            @RequestParam Integer idempresa
    ) {
        Tarifas tarifaEditada =
                tarifaEscritorioService.editar(idtarifas, dto, idempresa);
        return ResponseEntity.ok(tarifaEditada);
    }

    // ===============================
    // ELIMINAR TARIFA
    // ===============================
    @DeleteMapping("/{idtarifas}")
    public ResponseEntity<?> eliminarTarifa(
            @PathVariable Integer idtarifas,
            @RequestParam Integer idempresa
    ) {
        boolean eliminado =
                tarifaEscritorioService.eliminar(idtarifas, idempresa);

        if (eliminado) {
            return ResponseEntity.ok()
                    .body("{\"mensaje\":\"Tarifa eliminada correctamente\"}");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"error\":\"Tarifa no encontrada para esta empresa\"}");
    }


    // ===============================
    // COMBO: TIPOS DE SERVICIO
    // ===============================
    @GetMapping("/combo/tiposervicio")
    public ResponseEntity<List<String>> comboTipoServicio(
            @RequestParam Integer idempresa
    ) {
        return ResponseEntity.ok(
                tarifaService.listarTiposServicio(idempresa)
        );
    }
}

