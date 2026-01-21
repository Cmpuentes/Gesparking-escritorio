package com.gesnnova.gserver.controller;


import com.gesnnova.gserver.dto.ApiResponse;
import com.gesnnova.gserver.dto.VehiculosActivosResponse;
import com.gesnnova.gserver.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/vehiculos/activos")
    public ResponseEntity<?> obtenerVehiculosActivos() {
        try {
            VehiculosActivosResponse data = vehiculoService.obtenerVehiculosActivos();
            return ResponseEntity.ok(new ApiResponse(true, "Vehículos activos encontrados", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error al obtener vehículos activos", null));
        }
    }
}
