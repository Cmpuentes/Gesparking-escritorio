package com.gesnnova.gserver.dto;

public class HistorialTurnoResult {

    private boolean success;
    private String message;
    private HistorialTurnoResponse data;

    public HistorialTurnoResult(boolean success, String message, HistorialTurnoResponse data) {
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

    public HistorialTurnoResponse getData() {
        return data;
    }

    public void setData(HistorialTurnoResponse data) {
        this.data = data;
    }
}
