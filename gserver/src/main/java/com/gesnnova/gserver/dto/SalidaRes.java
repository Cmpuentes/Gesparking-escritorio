package com.gesnnova.gserver.dto;

public class SalidaRes {

    private boolean success;
    private String message;
    private SalidaData data;

    public SalidaRes(boolean success, String message, SalidaData data) {
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

    public SalidaData getData() {
        return data;
    }

    public void setData(SalidaData data) {
        this.data = data;
    }
}
