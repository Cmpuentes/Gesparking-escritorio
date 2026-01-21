package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.InicioTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface InicioTurnoRepository extends JpaRepository<InicioTurno, Integer> {

    // Buscar si el empleado ya tiene un turno activo
    Optional<InicioTurno> findByEmpleadoAndEstado(String empleado, String estado);

    // Obtener el último número de turno (máximo)
    @Query("SELECT MAX(i.numeroTurno) FROM InicioTurno i")
    Integer obtenerUltimoNumeroTurno();

    @Query("SELECT i.estado FROM InicioTurno i WHERE i.numeroTurno = :numero_turno")
    String findEstadoByNumeroTurno(@Param("numeroTurno") int numero_turno);

    @Query(value = "SELECT turno FROM inicio_turno ORDER BY idturno DESC ", nativeQuery = true)
    List<String> findUltimosTurnos();

    Optional<InicioTurno> findByNumeroTurno(int numeroTurno);
}
