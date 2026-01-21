package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngresoRepository extends JpaRepository<Ingreso, Integer> {

    Optional<Ingreso> findByPlacaAndEstado(String placa, String estado);

    List<Ingreso> findByNumeroturnoAndEstadoOrderByFechaingresoAsc(int numeroturno, String estado);

    List<Ingreso> findByEstadoOrderByFechaingresoAsc(String estado);

    @Query("SELECT COUNT(i) FROM Ingreso i WHERE i.numeroturno = :numeroTurno")
    int countVehiculosByNumeroTurno(int numeroTurno);

}
