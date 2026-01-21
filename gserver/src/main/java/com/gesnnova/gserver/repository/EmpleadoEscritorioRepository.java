package com.gesnnova.gserver.repository;

import com.gesnnova.gserver.dto.EmpleadoLoginResponseDTO;
import com.gesnnova.gserver.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmpleadoEscritorioRepository extends JpaRepository<Empleado, Integer> {

    // LISTAR POR EMPRESA
    List<Empleado> findByEmpresa_Idempresa(Integer idempresa);

    List<Empleado> findByEmpresa_IdempresaAndDocumentoContainingIgnoreCase(
            Integer idempresa, String documento
    );

    // VALIDAR QUE EL EMPLEADO PERTENEZCA A LA EMPRESA
    Optional<Empleado> findByIdempleadoAndEmpresa_Idempresa(
            Integer idempleado, Integer idempresa
    );

    // LOGIN
    @Query("""
        SELECT new com.gesnnova.gserver.dto.EmpleadoLoginResponseDTO(
            em.empresa.idempresa,
            em.empresa.nombre,
            CONCAT(em.nombres, ' ', em.apellidos),
            em.acceso
        )
        FROM Empleado em
        WHERE em.login = :login
          AND em.password = :password
    """)
    Optional<EmpleadoLoginResponseDTO> loginYObtenerEmpresa(
            @Param("login") String login,
            @Param("password") String password
    );
}

