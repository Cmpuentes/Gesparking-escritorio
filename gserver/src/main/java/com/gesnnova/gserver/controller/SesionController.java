package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.CerrarSesionRequest;
import com.gesnnova.gserver.model.Sesiones;
import com.gesnnova.gserver.repository.EmpleadoRepository;
import com.gesnnova.gserver.repository.SesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class SesionController {

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping("/check-session")
    public ResponseEntity<Map<String, Object>> checkSession(@RequestParam String token) {
        Map<String, Object> response = new HashMap<>();

        // 1. Validar existencia del token
        var sesionOpt = sesionRepository.findByToken(token);

        if (sesionOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Sesión no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Sesiones sesion = sesionOpt.get();

        // 2. Verificar estado
        if (!"activo".equalsIgnoreCase(sesion.getEstado())) {
            response.put("success", false);
            response.put("message", "La sesión ha sido cerrada");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 3. Verificar expiración
        if (sesion.getExpiracion().isBefore(LocalDateTime.now())) {
            response.put("success", false);
            response.put("message", "La sesión ha expirado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // 4. Todo OK
        response.put("success", true);
        response.put("message", "Sesión válida");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> cerrarSesion(@RequestBody CerrarSesionRequest request) {
        Map<String, Object> response = new HashMap<>();

        Optional<Sesiones> sesionOpt = sesionRepository.findByToken(request.getToken());

        if (sesionOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Token inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Sesiones sesion = sesionOpt.get();

        if ("inactivo".equalsIgnoreCase(sesion.getEstado())) {
            response.put("success", false);
            response.put("message", "La sesión ya está cerrada");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        sesion.setEstado("inactivo");
        sesionRepository.save(sesion);

        response.put("success", true);
        response.put("message", "Sesión cerrada correctamente");
        return ResponseEntity.ok(response);
    }

}
