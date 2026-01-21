package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.IngresoRequest;
import com.gesnnova.gserver.dto.IngresoResponse;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    public ResponseEntity<?> registrarIngreso(IngresoRequest dto) {

        // 1. Validación básica: Placa es obligatoria
        if (dto.getPlaca() == null || dto.getPlaca().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La placa es obligatoria");
        }

        // 2. Validar si ya existe un ingreso activo con esta placa
        Optional<Ingreso> existenteOpt = ingresoRepository.findByPlacaAndEstado(dto.getPlaca(), "Activo");

        if (existenteOpt.isPresent()) {
            return ResponseEntity.status(409).body("Vehículo ya en parqueadero");
        }

        // 3. Mapear DTO a entidad
        Ingreso ingreso = new Ingreso();
        ingreso.setTurno(dto.getTurno());
        ingreso.setPlaca(dto.getPlaca());
        ingreso.setFechaingreso(dto.getFechaingreso());
        ingreso.setTipovehiculo(dto.getTipovehiculo());
        ingreso.setTiposervicio(dto.getTiposervicio());
        ingreso.setCliente(dto.getCliente());
        ingreso.setZona(dto.getZona());
        ingreso.setObservaciones(dto.getObservaciones());
        ingreso.setEstado("Activo");
        ingreso.setNumeroturno(dto.getNumeroturno());
        ingreso.setEmpleado(dto.getEmpleado());

        // 4. Guardar en base de datos y responder con datos para impresión
        try {
            ingresoRepository.save(ingreso);

            IngresoResponse response = new IngresoResponse();
            response.setPlaca(ingreso.getPlaca());
            response.setFechaIngreso(ingreso.getFechaingreso());
            response.setCliente(ingreso.getCliente());
            response.setZona(ingreso.getZona());
            response.setTipoVehiculo(ingreso.getTipovehiculo());
            response.setNumeroTurno(ingreso.getNumeroturno());
            response.setEmpleado(ingreso.getEmpleado());

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Ingreso realizado con éxito");
            result.put("data", response);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar el ingreso");
        }
    }
}
