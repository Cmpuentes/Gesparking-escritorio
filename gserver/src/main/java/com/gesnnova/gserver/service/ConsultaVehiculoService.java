package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.ConsultaVehiculoResponse;
import com.gesnnova.gserver.dto.ConsultaVehiculoResult;
import com.gesnnova.gserver.dto.Tarifa;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.model.TiempoCalculado;
import com.gesnnova.gserver.repository.IngresoRepository;
import com.gesnnova.gserver.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Service
public class ConsultaVehiculoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    private final TiempoService tiempoService = new TiempoService();
    private final TarifaService tarifaService = new TarifaService();

    public ConsultaVehiculoResult consultarVehiculo(String placa) {
        Optional<Ingreso> optionalIngreso = ingresoRepository.findByPlacaAndEstado(placa, "Activo");

        if (optionalIngreso.isEmpty()) {
            return new ConsultaVehiculoResult(false, "La placa no está registrada en el parqueadero", null);
        }

        Ingreso ingreso = optionalIngreso.get();

        // Obtener fecha actual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", Locale.ENGLISH);
        String fechaActual = LocalDateTime.now().format(formatter);

        // Calcular tiempo transcurrido
        TiempoCalculado tiempo = tiempoService.calcularTiempo(ingreso.getFechaingreso(), fechaActual);

        Tarifa tarifa;

        // 👇 Aquí va la validación de Prepago
        if ("PREPAGO".equalsIgnoreCase(ingreso.getTiposervicio())
                || "CLIENTE HOTEL".equalsIgnoreCase(ingreso.getTiposervicio())
                || "EMPLEADO".equalsIgnoreCase(ingreso.getTiposervicio())
        || "VISITANTE".equalsIgnoreCase(ingreso.getTiposervicio())) {
            tarifa = new Tarifa();
            tarifa.setPreciohoras(0);
            tarifa.setPrecio12h(0);
            tarifa.setDescuentorecibo(0);
        } else {
            Optional<Tarifa> optionalTarifa = tarifaRepository.obtenerTarifa(
                    ingreso.getTiposervicio(),
                    ingreso.getTipovehiculo()
            );

            if (optionalTarifa.isEmpty()) {
                return new ConsultaVehiculoResult(false, "No se encontraron tarifas para este vehículo", null);
            }

            tarifa = optionalTarifa.get();
        }

        // Calcular valor (prepago siempre dará 0)
        int valorCalculado = tarifaService.calcularCosto(
                tiempo,
                ingreso.getTiposervicio(),
                tarifa.getPreciohoras(),
                tarifa.getPrecio12h()
        );

        // Construir respuesta
        ConsultaVehiculoResponse response = new ConsultaVehiculoResponse();
        response.setPlaca(ingreso.getPlaca());
        response.setFechaIngreso(ingreso.getFechaingreso());
        response.setCliente(ingreso.getCliente());
        response.setTipoVehiculo(ingreso.getTipovehiculo());
        response.setTipoServicio(ingreso.getTiposervicio());
        response.setNumeroTurno(ingreso.getNumeroturno());
        response.setEmpleado(ingreso.getEmpleado());

        response.setDias(tiempo.getDias());
        response.setHoras(tiempo.getHoras());
        response.setMinutos(tiempo.getMinutos());
        response.setValor(valorCalculado);

        return new ConsultaVehiculoResult(true, "Consulta exitosa", response);
    }
}
