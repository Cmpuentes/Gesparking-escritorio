package com.gesnnova.gserver.dto;

import java.util.List;

public class VehiculosActivosResponse {

    private int total;
    private List<VehiculoActivoDTO> vehiculos;

    public VehiculosActivosResponse(int total, List<VehiculoActivoDTO> vehiculos) {
        this.total = total;
        this.vehiculos = vehiculos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<VehiculoActivoDTO> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<VehiculoActivoDTO> vehiculos) {
        this.vehiculos = vehiculos;
    }
}
