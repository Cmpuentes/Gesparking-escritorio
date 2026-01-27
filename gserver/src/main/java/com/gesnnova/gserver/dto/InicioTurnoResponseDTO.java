package com.gesnnova.gserver.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InicioTurnoResponseDTO {

    private Integer idturno;
    private String empleado;
    private String fechainicio;
    private String turno;
    private int numeroturno;
    private String estado;

    public InicioTurnoResponseDTO() {
    }


}
