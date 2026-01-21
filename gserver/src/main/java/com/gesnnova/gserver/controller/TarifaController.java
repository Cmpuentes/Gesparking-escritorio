package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.Tarifa;
import com.gesnnova.gserver.dto.TarifaRequest;
import com.gesnnova.gserver.dto.TarifaResponse;
import com.gesnnova.gserver.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaRepository tarifaRepository;

    @PostMapping
    public ResponseEntity<TarifaResponse> obtenerTarifa(@RequestBody TarifaRequest request) {
        String tipoServicio = request.getTipoServicio();
        String tipoVehiculo = request.getTipoVehiculo();

        if (tipoServicio == null || tipoServicio.isBlank() || tipoVehiculo == null || tipoVehiculo.isBlank()) {
            return ResponseEntity.badRequest().body(
                    new TarifaResponse(false, "Faltan parámetros: tipoServicio y tipoVehiculo son requeridos.", null)
            );
        }

        Optional<Tarifa> resultado = tarifaRepository.obtenerTarifa(tipoServicio, tipoVehiculo);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(new TarifaResponse(true, null, resultado.get()));
        } else {
            // 👇 Aquí en lugar de error devolvemos ceros
            Tarifa tarifaCero = new Tarifa();
            tarifaCero.setPrecio12h(0);
            tarifaCero.setPreciohoras(0);
            tarifaCero.setDescuentorecibo(0);

            return ResponseEntity.ok(
                    new TarifaResponse(true, "No se encontró tarifa, se asignan valores por defecto.", tarifaCero)
            );
        }
    }
}
