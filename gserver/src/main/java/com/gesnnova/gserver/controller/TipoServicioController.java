package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.model.TipoServicio;
import com.gesnnova.gserver.model.TipoVehiculo;
import com.gesnnova.gserver.repository.TipoServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos-servicio")
@CrossOrigin(origins = "*")
public class TipoServicioController {

    @Autowired
    private TipoServicioRepository tipoServicioRepository;

    @GetMapping
    public ResponseEntity<List<String>> obtenerTiposVehiculo() {
        List<TipoServicio> tipos = tipoServicioRepository.findAll();
        List<String> nombres = tipos.stream()
                .map(TipoServicio::getNombre)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nombres);
    }
}
