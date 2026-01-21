package com.gesnnova.gserver.dto;

public class IngresoData {

    private String placa;
    private String fechaIngreso;
    private String cliente;
    private String zona;
    private String tipovehiculo;
    private int numeroTurno;
    private String empleado;

    public IngresoData(String placa, String fechaIngreso, String cliente, String zona, String tipovehiculo, int numeroTurno, String empleado) {
        this.placa = placa;
        this.fechaIngreso = fechaIngreso;
        this.cliente = cliente;
        this.zona = zona;
        this.tipovehiculo = tipovehiculo;
        this.numeroTurno = numeroTurno;
        this.empleado = empleado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(String tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }
}
