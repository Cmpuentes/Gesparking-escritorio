package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.ClienteDTO;
import com.gesnnova.gserver.dto.ClienteRequestDTO;
import com.gesnnova.gserver.model.Cliente;
import com.gesnnova.gserver.repository.ClienteEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteEscritorioService {

    @Autowired
    private ClienteEscritorioRepository clienteEscritorioRepository;

    //FUNCIÓN PARA CONSULTAR CLIENTES PREPAGO SEGÚN LA PLACA EN INGRESO
    public String obtenerNombreClientePorPlaca(String placa) {
        return clienteEscritorioRepository.findByPlaca(placa)
                .filter(cliente -> "PREPAGO".equalsIgnoreCase(cliente.getTipoServicio()))
                .map(Cliente::getCliente)
                .orElse("No prepago");
    }

    public List<String> listarClientesUnicos() {
        return clienteEscritorioRepository.obtenerClientesUnicos();
    }

    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteEscritorioRepository.findAllByOrderByIdclienteDesc();

        return clientes.stream().map(c -> {
            ClienteDTO dto = new ClienteDTO();
            dto.setIdcliente(c.getIdcliente());
            dto.setFecha(c.getFecha());
            dto.setPlaca(c.getPlaca());
            dto.setTipovehiculo(c.getTipoVehiculo());
            dto.setTiposervicio(c.getTipoServicio());
            dto.setCliente(c.getCliente());
            dto.setTelefono(c.getTelefono());
            dto.setTarifas(c.getTarifas());
            dto.setEstado(c.getEstado());
            dto.setObservaciones(c.getObservaciones());
            return dto;
        }).toList();
    }

    public boolean eliminarCliente(Integer id) {
        if (clienteEscritorioRepository.existsById(id)) {
            clienteEscritorioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Cliente guardarCliente(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setFecha(dto.getFecha());
        cliente.setPlaca(dto.getPlaca());
        cliente.setTipoVehiculo(dto.getTipovehiculo());
        cliente.setTipoServicio(dto.getTiposervicio());
        cliente.setCliente(dto.getCliente());
        cliente.setTelefono(dto.getTelefono());
        cliente.setTarifas(dto.getTarifas());
        cliente.setEstado(dto.getEstado());
        cliente.setObservaciones(dto.getObservaciones());
        return clienteEscritorioRepository.save(cliente);
    }

    public boolean editarCliente(Integer id, ClienteRequestDTO dto) {
        return clienteEscritorioRepository.findById(id).map(cliente -> {
            cliente.setFecha(dto.getFecha());
            cliente.setPlaca(dto.getPlaca());
            cliente.setTipoVehiculo(dto.getTipovehiculo());
            cliente.setTipoServicio(dto.getTiposervicio());
            cliente.setCliente(dto.getCliente());
            cliente.setTelefono(dto.getTelefono());
            cliente.setTarifas(dto.getTarifas());
            cliente.setEstado(dto.getEstado());
            cliente.setObservaciones(dto.getObservaciones());
            clienteEscritorioRepository.save(cliente);
            return true;
        }).orElse(false);
    }
}
