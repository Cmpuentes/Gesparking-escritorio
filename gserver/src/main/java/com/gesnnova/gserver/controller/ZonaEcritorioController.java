package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.ZonaAdminDTO;
import com.gesnnova.gserver.model.Zona;
import com.gesnnova.gserver.service.ZonaEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escritorio/zonas")
@CrossOrigin(origins = "*")
public class ZonaEcritorioController {

    @Autowired
    ZonaEscritorioService zonaEscritorioService;

    @GetMapping
    public ResponseEntity<List<Zona>> listar(@RequestParam(required = false) String numero) {
        return ResponseEntity.ok(zonaEscritorioService.listarPorNumero(numero));
    }

    @PostMapping
    public ResponseEntity<Zona> insertar(@RequestBody ZonaAdminDTO dto) {
        Zona nuevaZona = zonaEscritorioService.insertar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaZona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zona> editar(@PathVariable Integer id, @RequestBody ZonaAdminDTO dto) {
        try {
            Zona zonaEditada = zonaEscritorioService.editar(id, dto);
            return ResponseEntity.ok(zonaEditada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
