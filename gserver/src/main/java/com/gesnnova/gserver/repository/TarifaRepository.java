package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.dto.Tarifa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TarifaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<Tarifa> obtenerTarifa(String tipoServicio, String tipoVehiculo) {


        String sql = "SELECT precio12h, descuentorecibo, preciohoras FROM tarifas WHERE tiposervicio = ? AND tipovehiculo = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{tipoServicio, tipoVehiculo}, (rs, rowNum) -> {
                Tarifa tarifa = new Tarifa();
                tarifa.setPrecio12h(rs.getInt("precio12h"));
                tarifa.setDescuentorecibo(rs.getInt("descuentorecibo"));
                tarifa.setPreciohoras(rs.getInt("preciohoras"));
                return tarifa;
            }));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
