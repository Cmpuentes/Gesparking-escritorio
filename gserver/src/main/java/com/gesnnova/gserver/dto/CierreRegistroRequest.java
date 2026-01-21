package com.gesnnova.gserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CierreRegistroRequest {


    @JsonProperty("turno")
    private String turno;

    @JsonProperty("numeroturno")
    private int numeroTurno;

    @JsonProperty("empleado")
    private String empleado;

    @JsonProperty("fechaingreso")
    private String fechaIngreso;

    @JsonProperty("fechasalida")
    private String fechaSalida;

    @JsonProperty("recibidos")
    private int recibidos;

    @JsonProperty("totalvehiculos")
    private int totalVehiculos;

    @JsonProperty("base")
    private int base;

    @JsonProperty("efectivo")
    private int efectivo;

    @JsonProperty("tarjeta")
    private int tarjeta;

    @JsonProperty("transferencia")
    private int transferencia;

    @JsonProperty("otrosingresos")
    private int otrosIngresos;

    @JsonProperty("efectivoliquido")
    private int efectivoLiquido;

    @JsonProperty("totalrecaudado")
    private int totalRecaudado;

    @JsonProperty("observaciones")
    private String observaciones;

    @JsonProperty("totalabonos")
    private int totalAbonos;

    private int abonoEfectivo;

    private int abonoTarjeta;

    private int abonoTransferencia;

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getRecibidos() {
        return recibidos;
    }

    public void setRecibidos(int recibidos) {
        this.recibidos = recibidos;
    }

    public int getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(int totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
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

    public int getOtrosIngresos() {
        return otrosIngresos;
    }

    public void setOtrosIngresos(int otrosIngresos) {
        this.otrosIngresos = otrosIngresos;
    }

    public int getEfectivoLiquido() {
        return efectivoLiquido;
    }

    public void setEfectivoLiquido(int efectivoLiquido) {
        this.efectivoLiquido = efectivoLiquido;
    }

    public int getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(int totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getTotalAbonos() {
        return totalAbonos;
    }

    public void setTotalAbonos(int totalAbonos) {
        this.totalAbonos = totalAbonos;
    }

    public int getAbonoEfectivo() {
        return abonoEfectivo;
    }

    public void setAbonoEfectivo(int abonoEfectivo) {
        this.abonoEfectivo = abonoEfectivo;
    }

    public int getAbonoTarjeta() {
        return abonoTarjeta;
    }

    public void setAbonoTarjeta(int abonoTarjeta) {
        this.abonoTarjeta = abonoTarjeta;
    }

    public int getAbonoTransferencia() {
        return abonoTransferencia;
    }

    public void setAbonoTransferencia(int abonoTransferencia) {
        this.abonoTransferencia = abonoTransferencia;
    }
}
