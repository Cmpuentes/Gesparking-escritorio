package com.gesnnova.gserver.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sesiones")
public class Sesiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idempleado;

    @Column(name = "idempresa")
    private Integer idEmpresa;

    private String token;
    private LocalDateTime fecha_inicio;
    private LocalDateTime expiracion;
    private String estado;
}

