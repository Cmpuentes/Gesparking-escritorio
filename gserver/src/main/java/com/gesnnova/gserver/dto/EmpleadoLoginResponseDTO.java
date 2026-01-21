package com.gesnnova.gserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoLoginResponseDTO {

    private Integer idempresa;
    private String empresa;
    private String empleado;
    private String acceso;
}

