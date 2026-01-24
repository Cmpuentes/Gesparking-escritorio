package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.dto.ConteoActivoDTO;
import com.gesnnova.gserver.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngresoRepositoryEscritorio extends JpaRepository<Ingreso, Integer> {

    @Query("SELECT i FROM Ingreso i WHERE i.estado = 'Activo' AND " +
            "(i.placa LIKE %:buscar% OR i.empleado LIKE %:buscar%)")
    List<Ingreso> buscarIngresosActivos(@Param("buscar") String buscar);

    boolean existsByPlacaAndEstado(String placa, String estado);

    Optional<Ingreso> findByPlacaAndEstado(String placa, String estado);

    List<Ingreso> findByEstadoOrderByIdingresoDesc(String estado);

    @Query("SELECT i.placa FROM Ingreso i WHERE i.estado = 'Activo'")
    List<String> findPlacasActivas();

    //Corregido para que imprima en orden alfabetico
    List<Ingreso> findByEstadoOrderByPlacaAsc(String estado);

    //Contar vehículos activos en el parqueadero
    @Query("SELECT new com.gesnnova.gserver.dto.ConteoActivoDTO(" +
            "COALESCE(COUNT(i), 0)) " +
            "FROM Ingreso i " +
            "WHERE i.estado = 'Activo'")
    ConteoActivoDTO contarEstadoActivo();

}
