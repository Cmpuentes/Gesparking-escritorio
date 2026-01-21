package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.SalidaEscritorioDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoDTO;
import com.gesnnova.gserver.dto.TotalesSalidaDTO;
import com.gesnnova.gserver.model.Salida;
import com.gesnnova.gserver.repository.SalidaEscritorioRepository;
import com.gesnnova.gserver.service.SalidaEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salidas/escritorio")
public class SalidaEscritorioController {

    @Autowired
    private SalidaEscritorioRepository salidaEscritorioRepository;

    @Autowired
    private SalidaEscritorioService salidaEscritorioService;

    @GetMapping("/activas")
    public List<Salida> listarSalidasTurnoActivo() {
        return salidaEscritorioService.obtenerSalidasTurnoActivo();
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Salida> actualizarSalida(
            @PathVariable int id,
            @RequestParam("usuario") String usuarioApp,
            @RequestBody SalidaEscritorioDTO dto) {

        Salida salidaActualizada = salidaEscritorioService.editarSalida(id, dto, usuarioApp);
        return ResponseEntity.ok(salidaActualizada);
    }

    @GetMapping("/totales-calculados")
    public ResponseEntity<TotalesSalidaDTO> obtenerTotalesSalida() {
        TotalesSalidaDTO totales = salidaEscritorioService.obtenerTotalesSalida();
        return ResponseEntity.ok(totales);
    }

    @GetMapping("/generar-comprobante")
    public ResponseEntity<Integer> generarComprobante() {
        int numero = salidaEscritorioService.generarComprobante();
        return ResponseEntity.ok(numero);
    }

    @PostMapping("/registrar")
    public Salida registrarSalida(@RequestBody SalidaEscritorioDTO dto) {
        return salidaEscritorioService.registrarSalida(dto);
    }

    @GetMapping("/ultima-salida")
    public ResponseEntity<Salida> obtenerUltimaSalida() {
        Optional<Salida> ultimaSalida = salidaEscritorioRepository.findTopByOrderByIdsalidaDesc();
        return ultimaSalida.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prepagos")
    public ResponseEntity<List<Salida>> obtenerPrepagos() {
        List<Salida> prepagos = salidaEscritorioRepository.findByTiposervicioOrderByIdsalidaDesc("PREPAGO");
        return ResponseEntity.ok(prepagos);
    }


    @GetMapping("/reporte-dia")
    public ResponseEntity<List<Salida>> obtenerReporteDia(
            @RequestParam String turno1,
            @RequestParam String turno2,
            @RequestParam String turno3) {

        List<Salida> reporte = salidaEscritorioService.obtenerReporteDia(turno1, turno2, turno3);

        if (reporte.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/buscar-placa")
    public ResponseEntity<List<Salida>> buscarPorPlaca(@RequestParam(required = false) String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            // Si quieres devolver todo:
            List<Salida> resultados = salidaEscritorioRepository.findAllByOrderByIdsalidaDesc();
            return ResponseEntity.ok(resultados);

            // O si prefieres devolver lista vacía:
            // return ResponseEntity.ok(Collections.emptyList());
        }

        List<Salida> resultados = salidaEscritorioRepository.findByPlacaContainingOrderByIdsalidaDesc(placa);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/factura")
    public ResponseEntity<Salida> obtenerFacturaPorNumero(@RequestParam int numfactura) {
        Salida salida = salidaEscritorioService.obtenerFacturaPorNumero(numfactura);
        return ResponseEntity.ok(salida);
    }

    @GetMapping("/totales-medio-pago/{numeroTurno}")
    public ResponseEntity<TotalesMediosPagoDTO> obtenerTotalesMedioPago(
            @PathVariable int numeroTurno) {
        TotalesMediosPagoDTO totales = salidaEscritorioService.obtenerTotalesMedioPago(numeroTurno);
        return ResponseEntity.ok(totales);
    }

}
