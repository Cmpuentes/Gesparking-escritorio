package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.CierreRequestDTO;
import com.gesnnova.gserver.dto.CierreResponseDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoAbonosDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoDTO;
import com.gesnnova.gserver.model.SalidaTurno;
import com.gesnnova.gserver.repository.SalidaTurnoEscritorioRepository;
import com.gesnnova.gserver.service.AbonoEscritorioService;
import com.gesnnova.gserver.service.CierreService;
import com.gesnnova.gserver.service.SalidaEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cierre")
public class CierreController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CierreService cierreService;

    @Autowired
    private SalidaTurnoEscritorioRepository salidaTurnoEscritorioRepository;

    @Autowired
    private SalidaEscritorioService salidaEscritorioService;

    @Autowired
    private AbonoEscritorioService abonoEscritorioService;

    @PostMapping
    public ResponseEntity<?> calcularCierre(@RequestBody CierreRequestDTO request) {

        int numeroTurno = request.getNumeroTurno();

        if (numeroTurno <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "El número de turno es requerido"
            ));
        }

        try {
            // 1. Vehículos activos
            Integer totalVehiculos = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM ingreso WHERE estado = 'Activo'",
                    Integer.class
            );

            // 2. Totales consolidados (salida + abonos)
            TotalesMediosPagoDTO totalesMediosPago =
                    salidaEscritorioService.obtenerTotalesMedioPago(numeroTurno);

            // 3. Totales SOLO de abonos
            TotalesMediosPagoAbonosDTO totalesAbonos =
                    abonoEscritorioService.obtenerTotalesPorTurno(numeroTurno);

            // 4. Armar DTO
            CierreResponseDTO dto = new CierreResponseDTO();
            dto.setTotalVehiculosActivos(totalVehiculos != null ? totalVehiculos : 0);

            // Consolidados
            dto.setTotalEfectivo(totalesMediosPago.getTotalEfectivo());
            dto.setTotalTarjeta(totalesMediosPago.getTotalTarjeta());
            dto.setTotalTransferencia(totalesMediosPago.getTotalTransferencia());

            // Abonos
            long efectivoAbono = totalesAbonos.getEfectivo();
            long tarjetaAbono = totalesAbonos.getTarjeta();
            long transferenciaAbono = totalesAbonos.getTransferencia();

            dto.setTotalEfectivoAbono(
                    efectivoAbono > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) efectivoAbono
            );
            dto.setTotalTarjetaAbono(
                    tarjetaAbono > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) tarjetaAbono
            );
            dto.setTotalTransferenciaAbono(
                    transferenciaAbono > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) transferenciaAbono
            );

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", dto
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "success", false,
                    "message", "Error del servidor"
            ));
        }
    }


    @GetMapping("/vehiculos-recibidos/{numeroTurno}")
    public Map<String, Object> obtenerVehiculosRecibidos(@PathVariable int numeroTurno) {
        Map<String, Object> response = new HashMap<>();
        try {
            int cantidad = cierreService.obtenerVehiculosRecibidosPorTurno(numeroTurno);
            response.put("vehiculosRecibidos", cantidad);
        } catch (Exception e) {
            response.put("error", "Error al obtener los vehículos recibidos: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/cierreturno/ultimo")
    public ResponseEntity<SalidaTurno> obtenerUltimoCierre() {
        Optional<SalidaTurno> ultimo = salidaTurnoEscritorioRepository
                .findTopByOrderByIdfinturnoDesc();

        if (ultimo.isPresent()) {
            return ResponseEntity.ok(ultimo.get());
        } else {
            return ResponseEntity.noContent().build(); // 204 si no hay datos
        }
    }
}
