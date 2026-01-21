package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.ResumenTurnoDTO;
import com.gesnnova.gserver.repository.SalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumenTurnoService {

    @Autowired
    private SalidaRepository salidaRepository;


    public ResumenTurnoDTO obtenerTotalesPorTurno(int turno) {
        return salidaRepository
                .obtenerResumenPorTurno(turno)
                .orElseThrow(() ->
                        new RuntimeException("No se encontraron datos para el turno " + turno)
                );
    }
}