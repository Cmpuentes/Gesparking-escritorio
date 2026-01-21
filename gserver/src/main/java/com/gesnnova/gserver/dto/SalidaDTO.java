package com.gesnnova.gserver.dto;

public class SalidaDTO {

    private Integer idsalida;
    private String placa;
    private String tipoVehiculo;
    private String tipoServicio;
    private String fechaSalida;
    private int total;

    public SalidaDTO() {
    }

    public SalidaDTO(Integer idsalida, String placa, String tipoVehiculo, String tipoServicio, String fechaSalida, int total) {
        this.idsalida = idsalida;
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.tipoServicio = tipoServicio;
        this.fechaSalida = fechaSalida;
        this.total = total;
    }

    public SalidaDTO(String placa, String tipoVehiculo, String tipoServicio, String fechaSalida, int total) {
        this.placa = placa;
        this.tipoVehiculo = tipoVehiculo;
        this.tipoServicio = tipoServicio;
        this.fechaSalida = fechaSalida;
        this.total = total;
    }

    public Integer getIdsalida() {
        return idsalida;
    }

    public void setIdsalida(Integer idsalida) {
        this.idsalida = idsalida;
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

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}
