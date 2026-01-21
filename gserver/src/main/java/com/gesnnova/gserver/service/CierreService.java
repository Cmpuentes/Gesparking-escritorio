package com.gesnnova.gserver.service;

import com.gesnnova.gserver.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CierreService {

    @Autowired
    private IngresoRepository ingresoRepository;

    public int obtenerVehiculosRecibidosPorTurno(int numeroTurno) {
        return ingresoRepository.countVehiculosByNumeroTurno(numeroTurno);
    }
}
