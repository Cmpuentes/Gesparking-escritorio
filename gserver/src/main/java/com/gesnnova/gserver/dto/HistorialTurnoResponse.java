package com.gesnnova.gserver.dto;

import java.util.List;

public class HistorialTurnoResponse {

    private int totalIngresos;
    private int totalSalidas;
    private List<IngresoDTO> ingresos;
    private List<SalidaDTO> salidas;

    public HistorialTurnoResponse(int totalIngresos, int totalSalidas, List<IngresoDTO> ingresos, List<SalidaDTO> salidas) {
        this.totalIngresos = totalIngresos;
        this.totalSalidas = totalSalidas;
        this.ingresos = ingresos;
        this.salidas = salidas;
    }

    public int getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(int totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public int getTotalSalidas() {
        return totalSalidas;
    }

    public void setTotalSalidas(int totalSalidas) {
        this.totalSalidas = totalSalidas;
    }

    public List<IngresoDTO> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<IngresoDTO> ingrseos) {
        this.ingresos = ingrseos;
    }

    public List<SalidaDTO> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<SalidaDTO> salidas) {
        this.salidas = salidas;
    }
}
