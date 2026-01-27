package com.gesnnova.gserver.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "inicio_turno",
        indexes = {
                @Index(name = "idx_turno_empresa", columnList = "idempresa"),
                @Index(name = "idx_turno_empresa_estado", columnList = "idempresa, estado"),
                @Index(name = "idx_turno_empresa_numero", columnList = "idempresa, numero_turno")
        }
)
public class InicioTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idturno;

    @Column(nullable = false)
    private String empleado;

    @Column(name = "fecha_inicio", nullable = false)
    private String fechaInicio;

    @Column(nullable = false)
    private String turno;

    @Column(name = "numero_turno", nullable = false)
    private int numeroTurno;

    @Column(nullable = false)
    private String estado;

    @Column(name = "idempresa", nullable = false)
    private Integer idEmpresa;
}



