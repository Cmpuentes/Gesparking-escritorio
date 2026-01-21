package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {

    Optional<TipoVehiculo> findByNombre(String nombre);
}
