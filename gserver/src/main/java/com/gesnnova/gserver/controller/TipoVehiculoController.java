package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.model.TipoVehiculo;
import com.gesnnova.gserver.repository.TipoVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-vehiculo")
@CrossOrigin(origins = "*")
public class TipoVehiculoController {

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @GetMapping
    public ResponseEntity<List<String>> obtenerTiposVehiculo() {
        List<TipoVehiculo> tipos = tipoVehiculoRepository.findAll();
        List<String> nombres = tipos.stream()
                .map(TipoVehiculo::getNombre)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nombres);
    }
}
