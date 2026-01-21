package com.gesnnova.gserver.dto;

public class TarifaResponse {

    private boolean success;
    private String message;
    private Tarifa data;

    public TarifaResponse(boolean success, String message, Tarifa data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tarifa getData() {
        return data;
    }

    public void setData(Tarifa data) {
        this.data = data;
    }
}
