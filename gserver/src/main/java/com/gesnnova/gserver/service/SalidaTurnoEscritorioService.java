package com.gesnnova.gserver.service;

import com.gesnnova.gserver.ReportesPDF.SalidaTurnoReporteDTO;
import com.gesnnova.gserver.dto.SalidaTurnoDTO;
import com.gesnnova.gserver.dto.SalidaTurnoUpdateDTO;
import com.gesnnova.gserver.dto.TotalesMediosPagoAbonosDTO;
import com.gesnnova.gserver.model.SalidaTurno;
import com.gesnnova.gserver.repository.SalidaTurnoEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalidaTurnoEscritorioService {

    @Autowired
    private SalidaTurnoEscritorioRepository salidaTurnoEscritorioRepository;

    @Autowired
    private AbonoEscritorioService abonoService;

    public List<SalidaTurno> listar(String turno) {
        if (turno == null || turno.isBlank()) {
            return salidaTurnoEscritorioRepository.findAllByOrderByIdfinturnoDesc();
        }
        return salidaTurnoEscritorioRepository.findByTurnoContainingIgnoreCaseOrderByIdfinturnoDesc(turno);
    }

    public Optional<SalidaTurno> obtenerPorNumeroTurno(String numeroTurno) {
        return salidaTurnoEscritorioRepository.findByNumeroturno(numeroTurno);
    }

    public SalidaTurno guardarSalidaTurno(SalidaTurnoDTO dto) {
        SalidaTurno salida = new SalidaTurno();
        salida.setTurno(dto.getTurno());
        salida.setNumeroturno(dto.getNumeroturno());
        salida.setEmpleado(dto.getEmpleado());
        salida.setFechaingreso(dto.getFechaingreso());
        salida.setFechasalida(dto.getFechasalida());
        salida.setRecibos(dto.getRecibos());
        salida.setTotalvehiculos(dto.getTotalvehiculos());
        salida.setBase(dto.getBase());
        salida.setEfectivo(dto.getEfectivo());
        salida.setTarjeta(dto.getTarjeta());
        salida.setTransferencia(dto.getTransferencia());
        salida.setOtrosingresos(dto.getOtrosingresos());
        salida.setEfectivoliquido(dto.getEfectivoliquido());
        salida.setTotalrecaudado(dto.getTotalrecaudado());
        salida.setEstado(dto.getEstado());
        salida.setObservaciones(dto.getObservaciones());
        salida.setTotalabonos(dto.getTotalabonos());

        int numeroTurno;

        try {
            numeroTurno = Integer.parseInt(dto.getNumeroturno());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Número de turno inválido: " + dto.getNumeroturno()
            );
        }


        TotalesMediosPagoAbonosDTO totalesAbonos =
                abonoService.obtenerTotalesPorTurno(numeroTurno);

        try {
            salida.setAbonoEfectivo(Math.toIntExact(totalesAbonos.getEfectivo()));
            salida.setAbonoTarjeta(Math.toIntExact(totalesAbonos.getTarjeta()));
            salida.setAbonoTransferencia(Math.toIntExact(totalesAbonos.getTransferencia()));
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(
                    "Los valores de abonos exceden el límite permitido para el cierre de turno",
                    e
            );
        }




        return salidaTurnoEscritorioRepository.save(salida);
    }
    //  ✅ ✅ ✅  MÉTODO NUEVO PARA CIERRE DE TURNO
    public  SalidaTurnoReporteDTO obtenerUltimoCierre() {
        List<Object[]> result = salidaTurnoEscritorioRepository.findUltimoCierreNative();


        if (result == null || result.isEmpty()) {
            return null;
        }

        Object[] row = result.get(0);

        SalidaTurnoReporteDTO dto = new SalidaTurnoReporteDTO();

        dto.setEmpleado(asString(row[0]));
        dto.setTurno(asString(row[1]));
        dto.setFechaingreso(asString(row[2]));
        dto.setFechasalida(asString(row[3]));
        dto.setTotalvehiculos(asInt(row[4]));
        dto.setNumeroturno(asString(row[5]));
        dto.setBase(asInt(row[6]));
        dto.setEfectivo(asInt(row[7]));
        dto.setTarjeta(asInt(row[8]));
        dto.setTransferencia(asInt(row[9]));
        dto.setTotalabonos(asInt(row[10]));
        dto.setOtrosingresos(asInt(row[11]));
        dto.setEfectivoliquido(asInt(row[12]));
        dto.setTotalrecaudado(asInt(row[13]));
        dto.setObservaciones(asString(row[14]));

        return dto;
    }

    // --- Helpers de conversión ---
    private String asString(Object o) {
        return o == null ? null : o.toString();
    }

    private int asInt(Object o) {
        if (o == null) return 0;
        if (o instanceof Number) return ((Number) o).intValue();
        try {
            return Integer.parseInt(o.toString());
        } catch (Exception e) {
            return 0;
        }
    }
    public List<SalidaTurnoReporteDTO> obtenerCopiaCierreTurno(String Nturno) {
        List<Object[]> resultados = salidaTurnoEscritorioRepository.CopiaCierreTurno(Nturno);

        return resultados.stream().map(row -> {

            System.out.println("TIPOS DE FILA:");
            for (int i = 0; i < row.length; i++) {
                System.out.println("Columna " + i + " = " + (row[i] == null ? "null" : row[i].getClass().getName()));
            }

            SalidaTurnoReporteDTO dto = new SalidaTurnoReporteDTO();

            dto.setTurno((String) row[0]);
            dto.setNumeroturno((String) row[1]);
            dto.setEmpleado((String) row[2]);
            dto.setFechaingreso((String) row[3]);
            dto.setFechasalida((String) row[4]);
            dto.setTotalvehiculos(row[5] != null ? ((Number) row[5]).intValue() : 0);
            dto.setBase(row[6] != null ? ((Number) row[6]).intValue() : 0);
            dto.setEfectivo(row[7] != null ? ((Number) row[7]).intValue() : 0);
            dto.setTarjeta(row[8] != null ? ((Number) row[8]).intValue() : 0);
            dto.setTransferencia(row[9] != null ? ((Number) row[9]).intValue() : 0);
            dto.setOtrosingresos(row[10] != null ? ((Number) row[10]).intValue() : 0);
            dto.setEfectivoliquido(row[11] != null ? ((Number) row[11]).intValue() : 0);
            dto.setTotalrecaudado(row[12] != null ? ((Number) row[12]).intValue() : 0);
            dto.setObservaciones((String) row[13]);
            dto.setTotalabonos(row[14] != null ? ((Number) row[14]).intValue() : 0);

            return dto;
        }).toList();
    }

    // Listar todos, ordenados por numeroturno descendente (int)
    public List<SalidaTurno> listarOrdenadoPorNumeroDesc() {
        List<SalidaTurno> todos = salidaTurnoEscritorioRepository.findAll();

        // numeroturno es String en tu entidad: convertimos a int para ordenar numéricamente
        return todos.stream()
                .sorted(Comparator.comparingInt(this::parseNumeroTurno).reversed())
                .collect(Collectors.toList());
    }

    // helper: parsea numeroturno String a int, maneja fallos devolviendo 0
    private int parseNumeroTurno(SalidaTurno s) {
        if (s == null || s.getNumeroturno() == null) return 0;
        try {
            return Integer.parseInt(s.getNumeroturno().trim());
        } catch (NumberFormatException e) {
            // si no se puede parsear, intenta quitar caracteres no numéricos, o devuelve 0
            String digits = s.getNumeroturno().replaceAll("\\D+", "");
            if (digits.isEmpty()) return 0;
            try {
                return Integer.parseInt(digits);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    public SalidaTurno obtenerPorId(Integer id) {
        Optional<SalidaTurno> opt = salidaTurnoEscritorioRepository.findById(id);
        return opt.orElse(null);
    }

    public SalidaTurno actualizar(Integer id, SalidaTurnoUpdateDTO dto) {
        SalidaTurno existente = obtenerPorId(id);
        if (existente == null) return null;

        // No modificamos idfinturno
        existente.setTurno(dto.getTurno());
        existente.setNumeroturno(dto.getNumeroturno());
        existente.setEmpleado(dto.getEmpleado());
        existente.setFechaingreso(dto.getFechaingreso());
        existente.setFechasalida(dto.getFechasalida());
        existente.setRecibos(dto.getRecibos());
        existente.setTotalvehiculos(dto.getTotalvehiculos());
        existente.setBase(dto.getBase());
        existente.setEfectivo(dto.getEfectivo());
        existente.setTarjeta(dto.getTarjeta());
        existente.setTransferencia(dto.getTransferencia());
        existente.setOtrosingresos(dto.getOtrosingresos());
        existente.setEfectivoliquido(dto.getEfectivoliquido());
        existente.setTotalrecaudado(dto.getTotalrecaudado());
        existente.setEstado(dto.getEstado());
        existente.setObservaciones(dto.getObservaciones());
        existente.setTotalabonos(dto.getTotalabonos());

        return salidaTurnoEscritorioRepository.save(existente);
    }

    // Eliminación lógica: cambiar estado a "Eliminado"
    public boolean eliminarLogico(Integer id) {
        SalidaTurno existente = obtenerPorId(id);
        if (existente == null) return false;

        existente.setEstado("Eliminado");
        salidaTurnoEscritorioRepository.save(existente);
        return true;
    }
}
