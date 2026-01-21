package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.VehiculoActivoDTO;
import com.gesnnova.gserver.dto.VehiculosActivosResponse;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    // VehiculoService.java
    public VehiculosActivosResponse obtenerVehiculosActivos() throws SQLException {
        List<Ingreso> activos = ingresoRepository.findByEstadoOrderByFechaingresoAsc("Activo");

        List<VehiculoActivoDTO> vehiculos = activos.stream()
                .map(i -> new VehiculoActivoDTO(
                        i.getPlaca(),
                        i.getTipovehiculo(),
                        i.getTiposervicio(),
                        i.getFechaingreso(),
                        i.getZona()
                ))
                .collect(Collectors.toList());

        return new VehiculosActivosResponse(vehiculos.size(), vehiculos);
    }

}
