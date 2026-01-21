package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.ZonaAdminDTO;
import com.gesnnova.gserver.model.Zona;
import com.gesnnova.gserver.repository.ZonaEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZonaEscritorioService {

    @Autowired
    private ZonaEscritorioRepository zonaEscritorioRepository;

    public List<Zona> listarPorNumero(String numero) {
        if (numero == null || numero.isEmpty()) {
            return zonaEscritorioRepository.findAll(Sort.by("idzona"));
        }
        return zonaEscritorioRepository.findByNumeroContainingIgnoreCaseOrderByIdzona(numero);
    }

    //Se usa en el modulo de administrativos
    public Zona insertar(ZonaAdminDTO dto) {
        Zona zona = new Zona();
        zona.setEstado(dto.getEstado());
        zona.setNumero(dto.getNumero());
        zona.setOtro(dto.getOtro() != null ? dto.getOtro() : "No aplica");
        return zonaEscritorioRepository.save(zona);
    }

    //Se usa en el modulo de administrativos
    public Zona editar(Integer id, ZonaAdminDTO dto) {
        return zonaEscritorioRepository.findById(id).map(zona -> {
            zona.setEstado(dto.getEstado());
            zona.setNumero(dto.getNumero());
            // El campo "otro" no se edita porque es fijo ("No aplica")
            return zonaEscritorioRepository.save(zona);
        }).orElseThrow(() -> new RuntimeException("Zona no encontrada"));
    }
}
