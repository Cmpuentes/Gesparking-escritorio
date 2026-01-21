package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.BloqueosDTO;
import com.gesnnova.gserver.model.Bloqueos;
import com.gesnnova.gserver.repository.BloqueoEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloqueoEscritorioService {

    @Autowired
    private BloqueoEscritorioRepository bloqueoEscritorioRepository;

    public boolean estaBloqueado(String codigo){
        return bloqueoEscritorioRepository.findByCodigo(codigo).isPresent();
    }

    public List<Bloqueos> listarBloqueos(String buscar) {
        if (buscar == null || buscar.isEmpty()) {
            return bloqueoEscritorioRepository.findAll();
        } else {
            return bloqueoEscritorioRepository.findByCodigoContainingIgnoreCase(buscar);
        }
    }

    public Bloqueos insertar(BloqueosDTO dto) {
        Bloqueos bloqueo = new Bloqueos();
        bloqueo.setTipobloqueo(dto.getTipobloqueo());
        bloqueo.setCodigo(dto.getCodigo());
        return bloqueoEscritorioRepository.save(bloqueo);
    }

    public Bloqueos editar(Integer id, BloqueosDTO dto) {
        return bloqueoEscritorioRepository.findById(id)
                .map(bloqueo -> {
                    bloqueo.setTipobloqueo(dto.getTipobloqueo());
                    bloqueo.setCodigo(dto.getCodigo());
                    return bloqueoEscritorioRepository.save(bloqueo);
                })
                .orElseThrow(() -> new RuntimeException("Bloqueo no encontrado con id: " + id));
    }

    //Usada en el Modulo de funciones administrativas
    public boolean eliminarBloqueos(Integer idbloqueo) {
        if (bloqueoEscritorioRepository.existsById(idbloqueo)) {
            bloqueoEscritorioRepository.deleteById(idbloqueo);
            return true;
        }
        return false;
    }
}
