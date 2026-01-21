package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.*;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.model.Salida;
import com.gesnnova.gserver.repository.IngresoRepository;
import com.gesnnova.gserver.repository.SalidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistorialTurnoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private SalidaRepository salidaRepository;

    public HistorialTurnoResult obtenerHistorialPorTurno(int numeroTurno) {
        List<Ingreso> ingresos = ingresoRepository.findByNumeroturnoAndEstadoOrderByFechaingresoAsc(numeroTurno, "Activo");
        List<Salida> salidas = salidaRepository.findByTurnosalidaOrderByFechasalidaAsc(numeroTurno);

        List<IngresoDTO> ingresoDTOs = ingresos.stream().map(ing ->
                new IngresoDTO(
                        ing.getPlaca(),
                        ing.getTipovehiculo(),
                        ing.getTiposervicio(),
                        ing.getZona(),
                        ing.getFechaingreso()
                )
        ).collect(Collectors.toList());

        List<SalidaDTO> salidaDTOs = salidas.stream().map(sal ->
                new SalidaDTO(
                        sal.getIdsalida(),     // <- nuevo
                        sal.getPlaca(),
                        sal.getTipovehiculo(),
                        sal.getTiposervicio(),
                        sal.getFechasalida(),
                        sal.getTotal()
                )
        ).collect(Collectors.toList());

        HistorialTurnoResponse response = new HistorialTurnoResponse(
                ingresoDTOs.size(),
                salidaDTOs.size(),
                ingresoDTOs,
                salidaDTOs
        );

        return new HistorialTurnoResult(true, "Historial generado con éxito", response);
    }

    public Optional<SalidaResponseDTO> obtenerDetalleSalida(int idSalida) {
        return salidaRepository.obtenerDetallePorId(idSalida);
    }

}
