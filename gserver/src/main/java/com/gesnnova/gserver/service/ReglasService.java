package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.ReglasDTO;
import com.gesnnova.gserver.model.Reglas;
import com.gesnnova.gserver.model.TipoCobro;
import com.gesnnova.gserver.repository.ReglasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReglasService {

    private final ReglasRepository repository;

    public ReglasService(ReglasRepository repository) {
        this.repository = repository;
    }

    // ===== LISTAR POR EMPRESA =====
    public List<ReglasDTO> listar(Integer idempresa) {
        return repository.findByIdempresa(idempresa)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // ===== OBTENER POR ID =====
    public ReglasDTO obtener(Integer idreglas, Integer idempresa) {
        return repository.findByIdreglasAndIdempresa(idreglas, idempresa)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));
    }

    // ===== OBTENER POR SERVICIO =====
    public ReglasDTO obtenerPorServicio(String servicio, Integer idempresa) {
        return repository.findByTiposervicioAndIdempresa(servicio, idempresa)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));
    }

    // ===== CREAR =====
    public ReglasDTO guardar(ReglasDTO dto) {
        Reglas regla = toEntity(dto);
        return toDTO(repository.save(regla));
    }

    // ===== EDITAR =====
    public ReglasDTO editar(Integer idreglas, Integer idempresa, ReglasDTO dto) {

        Reglas regla = repository.findByIdreglasAndIdempresa(idreglas, idempresa)
                .orElseThrow(() -> new RuntimeException("Regla no encontrada"));

        regla.setTiposervicio(dto.getTiposervicio());
        regla.setTipocobro(TipoCobro.valueOf(String.valueOf(dto.getTipocobro())));
        regla.setAplicagracia(dto.getAplicagracia());
        regla.setMaxhoras(dto.getMaxhoras());
        regla.setMinutogracia(dto.getMinutogracia());
        regla.setMinutoextra(dto.getMinutoextra());
        regla.setCobrobloques(dto.getCobrobloques());

        return toDTO(repository.save(regla));
    }

    // ===== ELIMINAR =====
    public void eliminar(Integer idreglas, Integer idempresa) {

        if (!repository.existsByIdreglasAndIdempresa(idreglas, idempresa)) {
            throw new RuntimeException("Regla no encontrada para esta empresa");
        }

        Reglas regla = repository.findByIdreglasAndIdempresa(idreglas, idempresa).get();
        repository.delete(regla);
    }

    // ===== CONVERSIONES =====

    private ReglasDTO toDTO(Reglas r) {
        ReglasDTO dto = new ReglasDTO();
        dto.setIdreglas(r.getIdreglas());
        dto.setTiposervicio(r.getTiposervicio());
        dto.setTipocobro(TipoCobro.valueOf(r.getTipocobro().name()));
        dto.setAplicagracia(r.getAplicagracia());
        dto.setMaxhoras(r.getMaxhoras());
        dto.setMinutogracia(r.getMinutogracia());
        dto.setMinutoextra(r.getMinutoextra());
        dto.setCobrobloques(r.getCobrobloques());
        dto.setIdempresa(r.getIdempresa());
        return dto;
    }

    private Reglas toEntity(ReglasDTO dto) {
        Reglas r = new Reglas();
        r.setIdreglas(dto.getIdreglas());
        r.setTiposervicio(dto.getTiposervicio());
        r.setTipocobro(TipoCobro.valueOf(String.valueOf(dto.getTipocobro())));
        r.setAplicagracia(dto.getAplicagracia());
        r.setMaxhoras(dto.getMaxhoras());
        r.setMinutogracia(dto.getMinutogracia());
        r.setMinutoextra(dto.getMinutoextra());
        r.setCobrobloques(dto.getCobrobloques());
        r.setIdempresa(dto.getIdempresa());
        return r;
    }
}

