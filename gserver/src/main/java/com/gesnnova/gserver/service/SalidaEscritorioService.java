package com.gesnnova.gserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gesnnova.gserver.dto.SalidaEscritorioDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoProjection;
import com.gesnnova.gserver.dto.TotalesSalidaDTO;
import com.gesnnova.gserver.model.AuditoriaSalida;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.model.Salida;
import com.gesnnova.gserver.repository.AuditoriaSalidaRepository;
import com.gesnnova.gserver.repository.IngresoRepositoryEscritorio;
import com.gesnnova.gserver.repository.SalidaEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalidaEscritorioService {

    @Autowired
    private IngresoRepositoryEscritorio ingresoRepositoryEscritorio;

    @Autowired
    private SalidaEscritorioRepository salidaEscritorioRepository;

    @Autowired
    private AuditoriaSalidaRepository auditoriaSalidaRepository;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir los datos a JSON

    public List<Salida> obtenerSalidasTurnoActivo() {
        return salidaEscritorioRepository.findSalidasTurnoActivo();
    }

    public Salida editarSalida(int id, SalidaEscritorioDTO dto, String usuarioApp) {
        // 1️⃣ Buscar la salida original
        Salida salidaOriginal = salidaEscritorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida no encontrada con ID: " + id));

        try {
            // 2️⃣ Convertir los datos originales a JSON (antes de modificar)
            String datosAnteriores = objectMapper.writeValueAsString(salidaOriginal);

            // 3️⃣ Aplicar los nuevos valores
            salidaOriginal.setEfectivo(dto.getEfectivo());
            salidaOriginal.setTarjeta(dto.getTarjeta());
            salidaOriginal.setTransferencia(dto.getTransferencia());
            salidaOriginal.setTotal(dto.getTotal());

            // 4️⃣ Guardar la salida actualizada
            Salida salidaActualizada = salidaEscritorioRepository.save(salidaOriginal);

            // 5️⃣ Convertir los datos nuevos a JSON (ya actualizados)
            String datosNuevos = objectMapper.writeValueAsString(salidaActualizada);

            // 6️⃣ Crear el registro de auditoría
            AuditoriaSalida auditoria = new AuditoriaSalida();
            auditoria.setOperacion(AuditoriaSalida.Operacion.UPDATE);
            auditoria.setIdSalida(salidaOriginal.getIdsalida()); // relación con ingreso
            auditoria.setDatosAnteriores(datosAnteriores);
            auditoria.setDatosNuevos(datosNuevos);
            auditoria.setUsuarioApp(usuarioApp);
            auditoria.setIdEmpresa(0); // aún sin usar
            // La fecha se guarda automáticamente por el DEFAULT CURRENT_TIMESTAMP

            auditoriaSalidaRepository.save(auditoria);

            return salidaActualizada;

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar auditoría de salida (UPDATE): " + e.getMessage());
        }
    }

    public TotalesSalidaDTO obtenerTotalesSalida() {
        return salidaEscritorioRepository.obtenerTotalesSalida();
    }

    public int generarComprobante() {
        Integer maxFactura = salidaEscritorioRepository.obtenerMaxNumFactura();
        if (maxFactura == null || maxFactura == 0) {
            return 1; // primer valor si no hay registros
        }
        return maxFactura + 1;
    }

    public Salida registrarSalida(SalidaEscritorioDTO dto){

        Salida salida = new Salida();
        int ultimoNumero = salidaEscritorioRepository.obtenerMaxNumFactura();

        salida.setIdingreso(dto.getIdingreso());
        salida.setPlaca(dto.getPlaca());
        salida.setTipovehiculo(dto.getTipovehiculo());
        salida.setTiposervicio(dto.getTiposervicio());
        salida.setCliente(dto.getCliente());
        salida.setFechaentrada(dto.getFechaentrada());
        salida.setFechasalida(dto.getFechasalida());
        salida.setZona(dto.getZona());
        salida.setNumfactura(ultimoNumero + 1); // ✅ Aquí lo generas
        salida.setDias(dto.getDias());
        salida.setHoras(dto.getHoras());
        salida.setMinutos(dto.getMinutos());
        salida.setDescuento(dto.getDescuento());
        salida.setValor(dto.getValor());
        salida.setNumerorecibo(dto.getNumerorecibo());
        salida.setSubtotal(dto.getSubtotal());
        salida.setEfectivo(dto.getEfectivo());
        salida.setTarjeta(dto.getTarjeta());
        salida.setTransferencia(dto.getTransferencia());
        salida.setTotal(dto.getTotal());
        salida.setTurno(dto.getTurno());
        salida.setTurnoentrada(dto.getTurnoentrada());
        salida.setEmpleadoentrada(dto.getEmpleadoentrada());
        salida.setTurnosalida(dto.getTurnosalida());
        salida.setEmpleadosalida(dto.getEmpleadosalida());

        // Buscar el ingreso por su id
        Ingreso ingreso = ingresoRepositoryEscritorio.findById(dto.getIdingreso())
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado con ID: " + dto.getIdingreso()));

        // Cambiar estado a "Finalizado"
        ingreso.setEstado("Finalizado");

        // Guardar el ingreso actualizado
        ingresoRepositoryEscritorio.save(ingreso);

        return salidaEscritorioRepository.save(salida);
    }

    public List<Salida> obtenerReporteDia(String turno1, String turno2, String turno3) {
        return salidaEscritorioRepository.findByTurnos(turno1, turno2, turno3);
    }

    public Salida obtenerFacturaPorNumero(int numfactura) {
        return salidaEscritorioRepository.findByNumfactura(numfactura)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Factura no encontrada"
                ));
    }

    public TotalesMediosPagoDTO obtenerTotalesMedioPago(int numeroTurno) {
        TotalesMediosPagoProjection projection = salidaEscritorioRepository.obtenerTotalesMedioPago(numeroTurno);
        return new TotalesMediosPagoDTO(
                projection.getTotalEfectivo(),
                projection.getTotalTarjeta(),
                projection.getTotalTransferencia()
        );
    }
}
