package com.gesnnova.gserver.service;

import com.gesnnova.gserver.repository.InicioTurnoEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InicioTurnoEscritorioService {

    @Autowired
    private InicioTurnoEscritorioRepository inicioTurnoEscritorioRepository;

    public boolean finalizarTurno(int numeroTurno) {
        int updatedTurno = inicioTurnoEscritorioRepository.finalizarTurno(numeroTurno);
        int updatedSesion = inicioTurnoEscritorioRepository.cerrarSesionActiva();
        return updatedTurno > 0 && updatedSesion > 0;
    }
}
