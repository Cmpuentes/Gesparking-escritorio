package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.SalidaData;
import com.gesnnova.gserver.dto.SalidaRequest;
import com.gesnnova.gserver.dto.SalidaRes;
import com.gesnnova.gserver.repository.SalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/salida")
public class SalidaController {

    @Autowired
    private SalidaRepository salidaRepository;

    @PostMapping
    public ResponseEntity<SalidaRes> consultarSalida(@RequestBody SalidaRequest request) {
        String placa = request.getPlaca();

        if (placa == null || placa.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new SalidaRes(false, "La placa es requerida", null));
        }

        Optional<SalidaData> resultado = salidaRepository.consultarPorPlaca(placa);

        if (resultado.isPresent()) {
            return ResponseEntity.ok(new SalidaRes(true, null, resultado.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SalidaRes(false, "No se encontró esta placa en el parqueadero", null));
        }
    }
}
