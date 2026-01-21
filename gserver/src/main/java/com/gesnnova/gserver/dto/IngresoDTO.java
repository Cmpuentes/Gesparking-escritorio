package com.gesnnova.gserver.dto;

public class IngresoDTO {

    private String placa;
    private String tipoVehiculo;
    private String tipoServicio;
    private String zona;
    private String fechaIngreso;

    public IngresoDTO(String placa, String tipoVehiculo, String tipoServicio, String zona, String fechaIngreso) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.tipoServicio = tipoServicio;
        this.zona = zona;
        this.fechaIngreso = fechaIngreso;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
