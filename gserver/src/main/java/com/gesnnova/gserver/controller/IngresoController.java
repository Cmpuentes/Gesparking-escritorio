package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.IngresoData;
import com.gesnnova.gserver.dto.IngresoRequest;
import com.gesnnova.gserver.repository.IngresoRepository;
import com.gesnnova.gserver.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingreso")
@CrossOrigin(origins = "*")
public class IngresoController {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private IngresoService ingresoService;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody IngresoRequest dto) {
        return ingresoService.registrarIngreso(dto);
    }

    @GetMapping("/activo")
    public ResponseEntity<IngresoData> obtenerIngresoActivo(@RequestParam String placa) {
        return ingresoRepository.findByPlacaAndEstado(placa, "Activo")
                .map(ingreso -> new IngresoData(
                        ingreso.getPlaca(),
                        ingreso.getFechaingreso(),
                        ingreso.getCliente(),
                        ingreso.getZona(),
                        ingreso.getTipovehiculo(),
                        ingreso.getNumeroturno(),
                        ingreso.getEmpleado()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
