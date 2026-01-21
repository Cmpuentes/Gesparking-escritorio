package com.gesnnova.gserver.dto;

public class InformeTotalesDTO {

    private int efectivo;
    private int tarjeta;
    private int transferencia;
    private int otrosIngresos;
    private int efectivoLiquido;
    private int totalRecaudado;

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
}
