package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.InicioTurnoResponseDTO;
import com.gesnnova.gserver.dto.InicioTurnoUpdate;
import com.gesnnova.gserver.model.InicioTurno;
import com.gesnnova.gserver.repository.InicioTurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InicioTurnoService {

    @Autowired
    private InicioTurnoRepository inicioTurnoRepository;

    public String obtenerEstadoTurno(int numeroTurno) {
        return inicioTurnoRepository.findEstadoByNumeroTurno(numeroTurno);
    }

    public Optional<InicioTurnoResponseDTO> realizarConsulta(int numeroTurno) {
        return inicioTurnoRepository.findByNumeroTurno(numeroTurno)
                .map(turno -> {
                    InicioTurnoResponseDTO dto = new InicioTurnoResponseDTO();
                    dto.setIdturno(turno.getIdturno());
                    dto.setEmpleado(turno.getEmpleado());
                    dto.setFechainicio(turno.getFecha_inicio());
                    dto.setTurno(turno.getTurno());
                    dto.setNumeroturno(turno.getNumeroTurno());
                    dto.setEstado(turno.getEstado());
                    return dto;
                });
    }

    //Modulo para editar y eliminar turno por parte de los administrativos
    public List<InicioTurno> listar() {
        return inicioTurnoRepository.findAll(Sort.by(Sort.Direction.DESC, "idturno"));
    }

    public InicioTurno obtenerPorId(Integer id) {
        return inicioTurnoRepository.findById(id)
                .orElse(null);
    }

    public InicioTurno actualizar(Integer id, InicioTurnoUpdate dto) {
        InicioTurno existente = obtenerPorId(id);

        if (existente == null) {
            return null;
        }

        existente.setEmpleado(dto.getEmpleado());
        existente.setFecha_inicio(dto.getFecha_inicio());
        existente.setTurno(dto.getTurno());
        existente.setNumeroTurno(dto.getNumeroTurno());
        existente.setEstado(dto.getEstado());

        return inicioTurnoRepository.save(existente);
    }

    public boolean eliminarLogico(Integer id) {
        InicioTurno existente = obtenerPorId(id);

        if (existente == null) {
            return false;
        }

        existente.setEstado("Eliminado");
        inicioTurnoRepository.save(existente);
        return true;
    }
}
