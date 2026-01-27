package com.gesnnova.gserver.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private String nombreCompleto;
    private String fecha_inicio; // Ahora es String, no LocalDateTime
    private String turno;
    private int numero_turno;
    private int idEmpresa;
    private String nombreEmpresa;



}
