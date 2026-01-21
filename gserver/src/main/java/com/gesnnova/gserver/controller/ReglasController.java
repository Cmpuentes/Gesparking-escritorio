package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.ReglasDTO;
import com.gesnnova.gserver.model.Reglas;
import com.gesnnova.gserver.service.ReglasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reglas")
public class ReglasController {

    private final ReglasService service;

    public ReglasController(ReglasService service) {
        this.service = service;
    }

    @GetMapping("/empresa/{idempresa}")
    public List<ReglasDTO> listar(@PathVariable Integer idempresa) {
        return service.listar(idempresa);
    }

    @GetMapping("/{idempresa}/{idreglas}")
    public ReglasDTO obtener(
            @PathVariable Integer idempresa,
            @PathVariable Integer idreglas) {
        return service.obtener(idreglas, idempresa);
    }

    @GetMapping("/servicio/{idempresa}/{servicio}")
    public ReglasDTO obtenerPorServicio(
            @PathVariable Integer idempresa,
            @PathVariable String servicio) {
        return service.obtenerPorServicio(servicio, idempresa);
    }

    @PostMapping
    public ReglasDTO crear(@RequestBody ReglasDTO dto) {
        return service.guardar(dto);
    }

    @PutMapping("/{idempresa}/{idreglas}")
    public ReglasDTO editar(
            @PathVariable Integer idempresa,
            @PathVariable Integer idreglas,
            @RequestBody ReglasDTO dto) {
        return service.editar(idreglas, idempresa, dto);
    }

    @DeleteMapping("/{idempresa}/{idreglas}")
    public void eliminar(
            @PathVariable Integer idempresa,
            @PathVariable Integer idreglas) {
        service.eliminar(idreglas, idempresa);
    }
}



