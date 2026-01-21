package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.EmpleadoAdminDTO;
import com.gesnnova.gserver.model.Empleado;
import com.gesnnova.gserver.service.EmpleadoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/escritorio/empleados")
public class EmpleadoEscritorioController {

    @Autowired
    private EmpleadoEscritorioService service;

    @GetMapping
    public ResponseEntity<List<Empleado>> listar(
            @RequestParam(required = false) String documento,
            @RequestParam Integer idempresa
    ) {
        return ResponseEntity.ok(
                service.listarPorDocumento(documento, idempresa)
        );
    }

    @PostMapping
    public ResponseEntity<EmpleadoAdminDTO> crear(
            @RequestBody EmpleadoAdminDTO dto,
            @RequestParam Integer idempresa
    ) {
        return ResponseEntity.ok(
                service.crearEmpleado(dto, idempresa)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> editar(
            @PathVariable Integer id,
            @RequestParam Integer idempresa,
            @RequestBody EmpleadoAdminDTO dto
    ) {
        return ResponseEntity.ok(
                service.actualizarEmpleado(id, dto, idempresa)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam Integer idempresa
    ) {
        service.eliminarEmpleado(id, idempresa);
        return ResponseEntity.noContent().build();
    }
}

