package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.InicioTurnoResponseDTO;
import com.gesnnova.gserver.dto.InicioTurnoUpdate;
import com.gesnnova.gserver.model.InicioTurno;
import com.gesnnova.gserver.repository.InicioTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InicioTurnoService {

    @Autowired
    private InicioTurnoRepository inicioTurnoRepository;

    // ===============================
    // INICIAR TURNO (MULTI EMPRESA)
    // ===============================
    @Transactional
    public InicioTurno iniciarTurno(
            String empleado,
            String turnoNombre,
            Integer idEmpresa
    ) {

        // 1️⃣ Verificar si ya hay turno activo para este empleado en la empresa
        Optional<InicioTurno> turnoActivoOpt =
                inicioTurnoRepository.findByEmpleadoAndIdEmpresaAndEstado(
                        empleado,
                        idEmpresa,
                        "Activo"
                );

        if (turnoActivoOpt.isPresent()) {
            return turnoActivoOpt.get(); // reutiliza turno activo
        }

        // 2️⃣ Obtener último número de turno por empresa
        Integer ultimoNumero =
                inicioTurnoRepository.obtenerUltimoNumeroTurnoPorEmpresa(idEmpresa);

        int nuevoNumeroTurno = (ultimoNumero != null ? ultimoNumero : 0) + 1;

        // 3️⃣ Crear nuevo turno
        InicioTurno nuevoTurno = new InicioTurno();
        nuevoTurno.setEmpleado(empleado);
        nuevoTurno.setFechaInicio(String.valueOf(LocalDateTime.parse(String.valueOf(LocalDateTime.now()))));
        nuevoTurno.setTurno(turnoNombre);
        nuevoTurno.setNumeroTurno(nuevoNumeroTurno);
        nuevoTurno.setEstado("Activo");
        nuevoTurno.setIdEmpresa(idEmpresa);

        return inicioTurnoRepository.save(nuevoTurno);
    }

    // ===============================
    // OBTENER ESTADO DE TURNO
    // ===============================
    public String obtenerEstadoTurno(int numeroTurno, Integer idEmpresa) {
        return inicioTurnoRepository
                .findEstadoByNumeroTurnoAndEmpresa(numeroTurno, idEmpresa);
    }

    // ===============================
    // CONSULTA DE TURNO
    // ===============================
    public Optional<InicioTurnoResponseDTO> realizarConsulta(
            int numeroTurno,
            Integer idEmpresa
    ) {
        return inicioTurnoRepository
                .findByNumeroTurnoAndIdEmpresa(numeroTurno, idEmpresa)
                .map(turno -> {
                    InicioTurnoResponseDTO dto = new InicioTurnoResponseDTO();
                    dto.setIdturno(turno.getIdturno());
                    dto.setEmpleado(turno.getEmpleado());
                    dto.setFechainicio(String.valueOf(turno.getFechaInicio()));
                    dto.setTurno(turno.getTurno());
                    dto.setNumeroturno(turno.getNumeroTurno());
                    dto.setEstado(turno.getEstado());
                    return dto;
                });
    }

    // ===============================
    // LISTAR TURNOS POR EMPRESA
    // ===============================
    public List<InicioTurno> listarPorEmpresa(Integer idEmpresa) {
        return inicioTurnoRepository.findAll(
                        Sort.by(Sort.Direction.DESC, "idturno")
                ).stream()
                .filter(t -> t.getIdEmpresa().equals(idEmpresa))
                .toList();
    }

    // ===============================
    // OBTENER POR ID (SEGURO)
    // ===============================
    public InicioTurno obtenerPorId(Integer id, Integer idEmpresa) {
        return inicioTurnoRepository.findById(id)
                .filter(t -> t.getIdEmpresa().equals(idEmpresa))
                .orElse(null);
    }

    // ===============================
    // ACTUALIZAR TURNO
    // ===============================
    @Transactional
    public InicioTurno actualizar(
            Integer id,
            Integer idEmpresa,
            InicioTurnoUpdate dto
    ) {
        InicioTurno existente = obtenerPorId(id, idEmpresa);

        if (existente == null) {
            return null;
        }

        existente.setEmpleado(dto.getEmpleado());
        existente.setFechaInicio(dto.getFecha_inicio());
        existente.setTurno(dto.getTurno());
        existente.setNumeroTurno(dto.getNumeroTurno());
        existente.setEstado(dto.getEstado());

        return inicioTurnoRepository.save(existente);
    }

    // ===============================
    // ELIMINACIÓN LÓGICA
    // ===============================
    @Transactional
    public boolean eliminarLogico(Integer id, Integer idEmpresa) {
        InicioTurno existente = obtenerPorId(id, idEmpresa);

        if (existente == null) {
            return false;
        }

        existente.setEstado("Eliminado");
        inicioTurnoRepository.save(existente);
        return true;
    }
}

