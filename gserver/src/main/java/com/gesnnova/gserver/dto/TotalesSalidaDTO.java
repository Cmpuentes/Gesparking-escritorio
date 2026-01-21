package com.gesnnova.gserver.dto;

public class TotalesSalidaDTO {

    private int efectivo;
    private int tarjeta;
    private int transferencia;

    public TotalesSalidaDTO() {
    }

    public TotalesSalidaDTO(Integer efectivo, Integer tarjeta, Integer transferencia) {
        this.efectivo = efectivo != null ? efectivo : 0;
        this.tarjeta = tarjeta != null ? tarjeta : 0;
        this.transferencia = transferencia != null ? transferencia : 0;
    }

    public TotalesSalidaDTO(int efectivo, int tarjeta, int transferencia) {
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.transferencia = transferencia;
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
}
