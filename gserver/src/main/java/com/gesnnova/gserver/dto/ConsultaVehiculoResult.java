package com.gesnnova.gserver.dto;

public class ConsultaVehiculoResult {

    private boolean success;                     // Indica si la consulta fue exitosa
    private String message;                      // Mensaje informativo o de error
    private ConsultaVehiculoResponse data;       // Los datos del vehículo consultado (si hay)

    // Constructor
    public ConsultaVehiculoResult(boolean success, String message, ConsultaVehiculoResponse data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters y Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public ConsultaVehiculoResponse getData() { return data; }
    public void setData(ConsultaVehiculoResponse data) { this.data = data; }
}
