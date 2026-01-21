package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.model.InicioTurno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InicioTurnoEscritorioRepository extends JpaRepository<InicioTurno, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE InicioTurno i SET i.estado = 'Finalizado' WHERE i.numeroTurno = :numeroTurno AND i.estado = 'Activo'")
    int finalizarTurno(@Param("numeroTurno") int numeroTurno);

    @Modifying
    @Transactional
    @Query("UPDATE Sesiones s SET s.estado = 'inactivo' WHERE s.estado = 'activo'")
    int cerrarSesionActiva();
}
