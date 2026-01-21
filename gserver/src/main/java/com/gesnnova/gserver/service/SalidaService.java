package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.ResumenTurnoDTO;
import com.gesnnova.gserver.dto.SalidaRequestDTO;
import com.gesnnova.gserver.dto.SalidaResponseDTO;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.model.Salida;
import com.gesnnova.gserver.repository.IngresoRepository;
import com.gesnnova.gserver.repository.SalidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalidaService {

    @Autowired
    private SalidaRepository salidaRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

    public SalidaResponseDTO registrarSalida(SalidaRequestDTO requestDTO) {
        validarCamposRequeridos(requestDTO);
        validarDatos(requestDTO);

        // Extraer datos del DTO
        Integer idingreso = requestDTO.getIdingreso();
        String placa = requestDTO.getPlaca();
        String tipovehiculo = requestDTO.getTipovehiculo();
        String tiposervicio = requestDTO.getTiposervicio();
        String cliente = requestDTO.getCliente();
        String fechaingreso = requestDTO.getFechaingreso();
        String fechasalida = requestDTO.getFechasalida();
        String zona = requestDTO.getZona();
        Integer dias = requestDTO.getDias();
        Integer horas = requestDTO.getHoras();
        Integer minutos = requestDTO.getMinutos();
        Integer costototal = requestDTO.getCostototal();
        Integer numerorecibo = requestDTO.getNumerorecibo();
        Integer descuento = requestDTO.getDescuento();
        Integer subtotal = requestDTO.getSubtotal();
        Integer efectivo = requestDTO.getEfectivo();
        Integer tarjeta = requestDTO.getTarjeta();
        Integer transferencia = requestDTO.getTransferencia();
        Integer total = requestDTO.getTotal();
        String turno = requestDTO.getTurno();
        Integer turnoentrada = requestDTO.getTurnoentrada();
        String empleadoentrada = requestDTO.getEmpleadoentrada();
        Integer turnosalida = requestDTO.getTurnosalida();
        String empleadosalida = requestDTO.getEmpleadosalida();

        // Generar número de factura
        Integer numFactura = salidaRepository.findMaxNumFactura() + 1;

        // Crear entidad Salida
        Salida salida = new Salida();
        salida.setIdingreso(idingreso);
        salida.setPlaca(placa);
        salida.setTipovehiculo(tipovehiculo);
        salida.setTiposervicio(tiposervicio);
        salida.setCliente(cliente);
        salida.setFechaentrada(fechaingreso);
        salida.setFechasalida(fechasalida);
        salida.setZona(zona);
        salida.setNumfactura(numFactura);
        salida.setDias(dias);
        salida.setHoras(horas);
        salida.setMinutos(minutos);
        salida.setValor(costototal);
        salida.setNumerorecibo(numerorecibo);
        salida.setDescuento(descuento);
        salida.setSubtotal(subtotal);
        salida.setEfectivo(efectivo);
        salida.setTarjeta(tarjeta);
        salida.setTransferencia(transferencia);
        salida.setTotal(total);
        salida.setTurno(turno);
        salida.setTurnoentrada(turnoentrada);
        salida.setEmpleadoentrada(empleadoentrada);
        salida.setTurnosalida(turnosalida);
        salida.setEmpleadosalida(empleadosalida);

        // Guardar en BD
        salidaRepository.insertarSalida(salida);

        // Actualizar estado del ingreso
        Ingreso ingreso = ingresoRepository.findById(idingreso)
                .orElseThrow(() -> new IllegalArgumentException("Ingreso no encontrado"));
        ingreso.setEstado("Finalizado");
        ingresoRepository.save(ingreso);

        // Retornar respuesta
        return new SalidaResponseDTO(salida);
    }

    private void validarCamposRequeridos(SalidaRequestDTO dto) {
        List<String> missingFields = new ArrayList<>();

        if (dto.getIdingreso() == null) missingFields.add("idingreso");
        if (dto.getPlaca() == null || dto.getPlaca().trim().isEmpty()) missingFields.add("placa");
        if (dto.getTipovehiculo() == null) missingFields.add("tipovehiculo");
        if (dto.getTiposervicio() == null) missingFields.add("tiposervicio");
        if (dto.getCliente() == null) missingFields.add("cliente");
        if (dto.getFechaingreso() == null) missingFields.add("fechaingreso");
        if (dto.getFechasalida() == null) missingFields.add("fechasalida");
        if (dto.getZona() == null) missingFields.add("zona");
        if (dto.getDias() == null) missingFields.add("dias");
        if (dto.getHoras() == null) missingFields.add("horas");
        if (dto.getMinutos() == null) missingFields.add("minutos");
        if (dto.getCostototal() == null) missingFields.add("costototal");
        if (dto.getNumerorecibo() == null) missingFields.add("numerorecibo");
        if (dto.getDescuento() == null) missingFields.add("descuento");
        if (dto.getSubtotal() == null) missingFields.add("subtotal");
        if (dto.getEfectivo() == null) missingFields.add("efectivo");
        if (dto.getTarjeta() == null) missingFields.add("tarjeta");
        if (dto.getTransferencia() == null) missingFields.add("transferencia");
        if (dto.getTotal() == null) missingFields.add("total");
        if (dto.getTurno() == null) missingFields.add("turno");
        if (dto.getTurnoentrada() == null) missingFields.add("turnoentrada");
        if (dto.getEmpleadoentrada() == null) missingFields.add("empleadoentrada");
        if (dto.getTurnosalida() == null) missingFields.add("turnosalida");
        if (dto.getEmpleadosalida() == null) missingFields.add("empleadosalida");

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("Campos requeridos faltantes: " + String.join(", ", missingFields));
        }
    }

    private void validarDatos(SalidaRequestDTO dto) {
        List<String> numericErrors = new ArrayList<>();

        if (dto.getDias() < 0) numericErrors.add("dias negativo");
        if (dto.getHoras() < 0) numericErrors.add("horas negativas");
        if (dto.getMinutos() < 0 || dto.getMinutos() > 59) numericErrors.add("minutos inválidos");
        if (dto.getCostototal() < 0) numericErrors.add("costototal negativo");
        if (dto.getDescuento() < 0) numericErrors.add("descuento negativo");
        if (dto.getSubtotal() < 0) numericErrors.add("subtotal negativo");
        if (dto.getEfectivo() < 0) numericErrors.add("efectivo negativo");
        if (dto.getTarjeta() < 0) numericErrors.add("tarjeta negativa");
        if (dto.getTransferencia() < 0) numericErrors.add("transferencia negativa");
        if (dto.getTotal() < 0) numericErrors.add("total negativo");

        if (!numericErrors.isEmpty()) {
            throw new IllegalArgumentException("Errores de validación numérica: " + String.join(", ", numericErrors));
        }

    }

}
