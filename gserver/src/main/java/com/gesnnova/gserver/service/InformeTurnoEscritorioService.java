package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.InformeDetalleDTO;
import com.gesnnova.gserver.dto.InformeTotalesDTO;
import com.gesnnova.gserver.repository.SalidaTurnoEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformeTurnoEscritorioService {

    @Autowired
    private SalidaTurnoEscritorioRepository salidaTurnoEscritorioRepository;

    public List<InformeDetalleDTO> obtenerDetalleDesde(String inicio) {
        List<Object[]> rows = salidaTurnoEscritorioRepository.detalleDesde(inicio);
        return rows.stream().map(r -> {
            InformeDetalleDTO dto = new InformeDetalleDTO();
            dto.setTurno((String) r[0]);
            dto.setNumeroTurno((String) r[1]);
            dto.setEmpleado((String) r[2]);
            dto.setFechaIngreso((String) r[3]);
            dto.setFechaSalida((String) r[4]);
            dto.setEfectivo(((Number) r[5]).intValue());
            dto.setTarjeta(((Number) r[6]).intValue());
            dto.setTransferencia(((Number) r[7]).intValue());
            dto.setOtrosIngresos(((Number) r[8]).intValue());
            dto.setEfectivoLiquido(((Number) r[9]).intValue());
            dto.setTotalRecaudado(((Number) r[10]).intValue());
            return dto;
        }).toList();
    }

    public List<InformeDetalleDTO> obtenerDetalleEntre(String inicio, String fin) {
        List<Object[]> rows = salidaTurnoEscritorioRepository.detalleEntre(inicio, fin);
        return rows.stream().map(r -> {
            InformeDetalleDTO dto = new InformeDetalleDTO();
            dto.setTurno((String) r[0]);
            dto.setNumeroTurno((String) r[1]);
            dto.setEmpleado((String) r[2]);
            dto.setFechaIngreso((String) r[3]);
            dto.setFechaSalida((String) r[4]);
            dto.setEfectivo(((Number) r[5]).intValue());
            dto.setTarjeta(((Number) r[6]).intValue());
            dto.setTransferencia(((Number) r[7]).intValue());
            dto.setOtrosIngresos(((Number) r[8]).intValue());
            dto.setEfectivoLiquido(((Number) r[9]).intValue());
            dto.setTotalRecaudado(((Number) r[10]).intValue());
            return dto;
        }).toList();
    }

    public InformeTotalesDTO obtenerTotalesDesde(String inicio) {
        List<Object[]> rows = salidaTurnoEscritorioRepository.totalesDesde(inicio);
        if (rows.isEmpty()) return new InformeTotalesDTO(); // vacío
        return mapToTotales(rows.get(0));
    }
    private InformeTotalesDTO mapToTotales(Object[] row) {
        InformeTotalesDTO dto = new InformeTotalesDTO();
        dto.setEfectivo(((Number) row[0]).intValue());
        dto.setTarjeta(((Number) row[1]).intValue());
        dto.setTransferencia(((Number) row[2]).intValue());
        dto.setOtrosIngresos(((Number) row[3]).intValue());
        dto.setEfectivoLiquido(((Number) row[4]).intValue());
        dto.setTotalRecaudado(((Number) row[5]).intValue());
        return dto;
    }

    public InformeTotalesDTO obtenerTotalesEntre(String inicio, String fin) {
        List<Object[]> rows = salidaTurnoEscritorioRepository.totalesEntre(inicio, fin);
        if (rows.isEmpty()) return new InformeTotalesDTO(); // vacío
        return mapToTotales(rows.get(0));
    }
}
