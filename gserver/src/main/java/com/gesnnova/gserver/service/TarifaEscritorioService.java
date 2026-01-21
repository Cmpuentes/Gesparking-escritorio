package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.TarifasAdminResDTO;
import com.gesnnova.gserver.dto.TarifasDTO;
import com.gesnnova.gserver.model.Tarifas;
import com.gesnnova.gserver.repository.TarifaEScritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TarifaEscritorioService {

    @Autowired
    TarifaEScritorioRepository tarifaEScritorioRepository;
    @Autowired
    private TarifaEScritorioRepository tarifaRepo;

    public TarifasDTO obtenerTarifa(
            String tipovehiculo,
            String tiposervicio,
            Integer idempresa
    ) {
        Tarifas tarifa = tarifaEScritorioRepository
                .findByTipovehiculoAndTiposervicioAndIdempresa(
                        tipovehiculo,
                        tiposervicio,
                        idempresa
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tarifa no encontrada para esta empresa"
                        )
                );

        return new TarifasDTO(
                tarifa.getPrecio12h(),
                tarifa.getPreciohoras(),
                tarifa.getDescuentorecibo()
        );
    }


    public List<Tarifas> listarPorTipoVehiculo(String buscar, Integer idempresa) {

        if (buscar == null || buscar.isEmpty()) {
            return tarifaEScritorioRepository
                    .findByIdempresaOrderByIdtarifas(idempresa);
        }

        return tarifaEScritorioRepository
                .findByTipovehiculoContainingIgnoreCaseAndIdempresaOrderByIdtarifas(
                        buscar,
                        idempresa
                );
    }


    //Usado en el modulo de administrativos
    public Tarifas guardarTarifa(TarifasAdminResDTO dto, Integer idEmpresa) {

        Tarifas tarifa = new Tarifas();
        tarifa.setTipovehiculo(dto.getTipovehiculo());
        tarifa.setTiposervicio(dto.getTiposervicio());
        tarifa.setPrecio12h(dto.getPrecio12h());
        tarifa.setDescuentorecibo(dto.getDescuentorecibo());
        tarifa.setPreciohoras(dto.getPreciohoras());
        tarifa.setIdempresa(idEmpresa); // 🔴 CLAVE

        return tarifaEScritorioRepository.save(tarifa);
    }


    //Usado en el modulo de administrativos
    public Tarifas editar(Integer idtarifas, TarifasAdminResDTO dto, Integer idempresa) {

        Tarifas tarifa = tarifaEScritorioRepository.findByIdtarifasAndIdempresa(idtarifas, idempresa)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Tarifa no encontrada para esta empresa"
                        )
                );

        tarifa.setTipovehiculo(dto.getTipovehiculo());
        tarifa.setTiposervicio(dto.getTiposervicio());
        tarifa.setPrecio12h(dto.getPrecio12h());
        tarifa.setDescuentorecibo(dto.getDescuentorecibo());
        tarifa.setPreciohoras(dto.getPreciohoras());

        return tarifaEScritorioRepository.save(tarifa);
    }


    //Usado en el modulo de administrativos
    public boolean eliminar(Integer idtarifas, Integer idempresa) {

        Tarifas tarifa = tarifaEScritorioRepository
                .findByIdtarifasAndIdempresa(idtarifas, idempresa)
                .orElse(null);

        if (tarifa != null) {
            tarifaEScritorioRepository.delete(tarifa);
            return true;
        }

        return false;
    }

// Listar todos los tipos de servicio en los combobx

    public List<String> listarTiposServicio(Integer idempresa) {
        return tarifaRepo.findTiposServicioByEmpresa(idempresa);
    }
}
