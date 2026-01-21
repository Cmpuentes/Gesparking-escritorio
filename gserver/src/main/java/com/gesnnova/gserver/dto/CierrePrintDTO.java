package com.gesnnova.gserver.dto;

public class CierrePrintDTO {

    private String turno;
    private int numeroturno;
    private String empleado;
    private String fechaingreso;
    private String fechasalida;
    private int totalvehiculos;
    private int efectivo;
    private int tarjeta;
    private int transferencia;
    private int otrosingresos;
    private int totalabonos;
    private int efectivoliquido;
    private int totalrecaudado;
    private String observaciones;

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getNumeroturno() {
        return numeroturno;
    }

    public void setNumeroturno(int numeroturno) {
        this.numeroturno = numeroturno;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(String fechasalida) {
        this.fechasalida = fechasalida;
    }

    public int getTotalvehiculos() {
        return totalvehiculos;
    }

    public void setTotalvehiculos(int totalvehiculos) {
        this.totalvehiculos = totalvehiculos;
    }

    public int getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(int efectivo) {
        this.efectivo = efectivo;
    }

    public int getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(int tarjeta) {
        this.tarjeta = tarjeta;
    }

    public int getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(int transferencia) {
        this.transferencia = transferencia;
    }

    public int getOtrosingresos() {
        return otrosingresos;
    }

    public void setOtrosingresos(int otrosingresos) {
        this.otrosingresos = otrosingresos;
    }

    public int getTotalabonos() {
        return totalabonos;
    }

    public void setTotalabonos(int totalabonos) {
        this.totalabonos = totalabonos;
    }

    public int getEfectivoliquido() {
        return efectivoliquido;
    }

    public void setEfectivoliquido(int efectivoliquido) {
        this.efectivoliquido = efectivoliquido;
    }

    public int getTotalrecaudado() {
        return totalrecaudado;
    }

    public void setTotalrecaudado(int totalrecaudado) {
        this.totalrecaudado = totalrecaudado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
