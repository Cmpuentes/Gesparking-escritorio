package com.gesnnova.gserver.dto;

public class ResumenTurnoDTO {

    private int vehiculosSalida;
    private int abonoEfectivo;
    private int abonoTarjeta;
    private int abonoTransferencia;
    private int totalAbonos;
    private int efectivo;
    private int tarjeta;
    private int transferencia;
    private int totalRecaudado;

    public ResumenTurnoDTO() {
    }

    public ResumenTurnoDTO(int vehiculosSalida, int abonoEfectivo, int abonoTarjeta, int abonoTransferencia, int totalAbonos, int efectivo, int tarjeta, int transferencia, int totalRecaudado) {
        this.vehiculosSalida = vehiculosSalida;
        this.abonoEfectivo = abonoEfectivo;
        this.abonoTarjeta = abonoTarjeta;
        this.abonoTransferencia = abonoTransferencia;
        this.totalAbonos = totalAbonos;
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.transferencia = transferencia;
        this.totalRecaudado = totalRecaudado;
    }

    public ResumenTurnoDTO(int vehiculosSalida, int efectivo, int tarjeta, int transferencia) {
        this.vehiculosSalida = vehiculosSalida;
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.transferencia = transferencia;
        this.totalRecaudado = efectivo + tarjeta + transferencia;
    }

    public ResumenTurnoDTO(int vehiculosSalida, int efectivo, int tarjeta, int transferencia, int totalRecaudado) {
        this.vehiculosSalida = vehiculosSalida;
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.transferencia = transferencia;
        this.totalRecaudado = totalRecaudado;
    }

    public int getVehiculosSalida() {
        return vehiculosSalida;
    }

    public void setVehiculosSalida(int vehiculosSalida) {
        this.vehiculosSalida = vehiculosSalida;
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

    public int getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(int totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
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

    public int getTotalAbonos() {
        return totalAbonos;
    }

    public void setTotalAbonos(int totalAbonos) {
        this.totalAbonos = totalAbonos;
    }
}
