package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.model.VistaAuditoriaIngreso;
import com.gesnnova.gserver.repository.VistaAuditoriaIngresoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditoria-ingreso")
@CrossOrigin(origins = "*")
public class AuditoriaIngresoController {

    private final VistaAuditoriaIngresoRepository repository;

    public AuditoriaIngresoController(VistaAuditoriaIngresoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    public List<VistaAuditoriaIngreso> listar() {
        return repository.findAll();
    }
}
