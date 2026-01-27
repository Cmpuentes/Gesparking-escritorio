package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.InicioTurno;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface InicioTurnoRepository extends JpaRepository<InicioTurno, Integer> {

    // Buscar si el empleado ya tiene un turno activo
    Optional<InicioTurno> findByEmpleadoAndIdEmpresaAndEstado(
            String empleado,
            Integer idEmpresa,
            String estado
    );


    // Obtener el último número de turno (máximo)
    @Query("SELECT MAX(i.numeroTurno) FROM InicioTurno i WHERE i.idEmpresa = :idEmpresa")
    Integer obtenerUltimoNumeroTurnoPorEmpresa(@Param("idEmpresa") Integer idEmpresa);


    @Query("SELECT i.estado FROM InicioTurno i WHERE i.numeroTurno = :numeroTurno AND i.idEmpresa = :idEmpresa")
    String findEstadoByNumeroTurnoAndEmpresa(
            @Param("numeroTurno") int numeroTurno,
            @Param("idEmpresa") Integer idEmpresa
    );



    @Query(value = "SELECT turno FROM inicio_turno WHERE idempresa = :idEmpresa ORDER BY idturno DESC",
    nativeQuery = true)
    List<String> findUltimosTurnosPorEmpresa(@Param("idEmpresa") Integer idEmpresa);


    Optional<InicioTurno> findByNumeroTurnoAndIdEmpresa(
            int numeroTurno,
            Integer idEmpresa
    );

}
