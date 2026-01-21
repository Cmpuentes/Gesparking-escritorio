package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/prepago/{placa}")
    public ResponseEntity<String> buscarClientePorPlaca(@PathVariable String placa) {
        return clienteRepository.findByPlaca(placa)
                .filter(cliente -> "Activo".equalsIgnoreCase(cliente.getEstado()))
                .map(cliente -> ResponseEntity.ok(cliente.getCliente()))
                .orElse(ResponseEntity.ok("No prepago"));
    }
}
