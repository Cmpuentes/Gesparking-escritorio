package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.SalidaRequestDTO;
import com.gesnnova.gserver.dto.SalidaResponseDTO;
import com.gesnnova.gserver.service.SalidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/registro")
public class SalidaRegistroController {

    @Autowired
    private SalidaService salidaService;

    @PostMapping("/salida")
    public ResponseEntity<Map<String, Object>> registrarSalida(@RequestBody SalidaRequestDTO requestDTO) {
        try {
            SalidaResponseDTO responseDTO = salidaService.registrarSalida(requestDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Salida registrada exitosamente");
            response.put("data", responseDTO);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error interno del servidor");
            return ResponseEntity.status(500).body(error);
        }
    }

}
