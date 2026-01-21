package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.PrepagoResponseDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoAbonosDTO;
import com.gesnnova.gserver.model.Abonos;
import com.gesnnova.gserver.model.Salida;
import com.gesnnova.gserver.repository.AbonoEscritorioRepository;
import com.gesnnova.gserver.repository.SalidaEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbonoEscritorioService {

    @Autowired
    private SalidaEscritorioRepository salidaEscritorioRepository;

    @Autowired
    private AbonoEscritorioRepository abonoEscritorioRepository;


    public PrepagoResponseDTO procesarPrepago(String cliente) {
        // 1. Buscar último consumo PREPAGO en salida
        Optional<Salida> ultimaSalida = salidaEscritorioRepository.findTopByClienteAndTiposervicioOrderByIdsalidaDesc(cliente, "PREPAGO");

        if (ultimaSalida.isEmpty()) {
            return new PrepagoResponseDTO(0, 0, "ERROR", "No se encontró consumo PREPAGO para el cliente");
        }

        int consumo = ultimaSalida.get().getValor();

        // 2. Buscar saldo en abonos
        Optional<Abonos> abonoOpt = abonoEscritorioRepository.findByCliente(cliente);
        if (abonoOpt.isEmpty()) {
            return new PrepagoResponseDTO(consumo, 0, "ERROR", "No se encontró saldo para el cliente");
        }

        Abonos abono = abonoOpt.get();
        int saldoActual = abono.getSaldo();

        // 3. Validar saldo
        if (saldoActual < consumo) {
            return new PrepagoResponseDTO(consumo, saldoActual, "ERROR",
                    "Saldo insuficiente para descontar " + consumo + ". Saldo actual: " + saldoActual);
        }

        // 4. Descontar saldo
        int saldoRestante = saldoActual - consumo;
        abono.setSaldo(saldoRestante);
        abonoEscritorioRepository.save(abono);

        return new PrepagoResponseDTO(consumo, saldoRestante, "OK", "Saldo descontado correctamente");
    }

    public List<Abonos> buscarAbonosPorCliente(String cliente) {
        return abonoEscritorioRepository.buscarPorCliente(cliente);
    }

    public Abonos guardarAbono(Abonos abono) {
        return abonoEscritorioRepository.save(abono);
    }

    public Abonos actualizarAbono(Integer id, Abonos abono) {
        return abonoEscritorioRepository.findById(id)
                .map(existing -> {
                    existing.setFecha(abono.getFecha());
                    existing.setCliente(abono.getCliente());
                    existing.setValor(abono.getValor());
                    existing.setEfectivo(abono.getEfectivo());
                    existing.setTarjeta(abono.getTarjeta());
                    existing.setTransferencia(abono.getTransferencia());
                    existing.setTotal(abono.getTotal());
                    existing.setObservaciones(abono.getObservaciones());
                    existing.setEmpleado(abono.getEmpleado());
                    existing.setTurno(abono.getTurno());
                    existing.setNumeroturno(abono.getNumeroturno());
                    existing.setSaldo(abono.getSaldo());
                    return abonoEscritorioRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Abono con id " + id + " no encontrado"));
    }

    public void eliminarAbono(Integer id) {
        if (!abonoEscritorioRepository.existsById(id)) {
            throw new RuntimeException("Abono con id " + id + " no encontrado");
        }
        abonoEscritorioRepository.deleteById(id);
    }

    //Metodo para traer los totales de abonos
    public TotalesMediosPagoAbonosDTO obtenerTotalesPorTurno(int numeroTurno) {
        return abonoEscritorioRepository.obtenerTotalesPorTurno(numeroTurno);
    }
}
