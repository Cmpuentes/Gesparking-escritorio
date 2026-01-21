package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.ClienteDTO;
import com.gesnnova.gserver.dto.ClienteRequestDTO;
import com.gesnnova.gserver.model.Cliente;
import com.gesnnova.gserver.service.ClienteEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/escritorio")
@CrossOrigin(origins = "*")
public class ClienteControllerEscritorio {

    @Autowired
    private ClienteEscritorioService clienteEscritorioService;

    @GetMapping("/buscar")
    public String buscarClientePorPlaca(@RequestParam String placa) {
        return clienteEscritorioService.obtenerNombreClientePorPlaca(placa);
    }

    @GetMapping("/unicos")
    public ResponseEntity<List<String>> obtenerClientesUnicos() {
        return ResponseEntity.ok(clienteEscritorioService.listarClientesUnicos());
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteEscritorioService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        boolean eliminado = clienteEscritorioService.eliminarCliente(id);
        if (eliminado) {
            return ResponseEntity.ok().body("Cliente eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente no encontrado");
        }
    }

    @PostMapping("/insertar")
    public ResponseEntity<Cliente> insertarCliente(@RequestBody ClienteRequestDTO dto) {
        Cliente clienteGuardado = clienteEscritorioService.guardarCliente(dto);
        return ResponseEntity.ok(clienteGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCliente(@PathVariable Integer id, @RequestBody ClienteRequestDTO dto) {
        boolean actualizado = clienteEscritorioService.editarCliente(id, dto);
        if (actualizado) {
            return ResponseEntity.ok("Cliente actualizado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }
    }
}
