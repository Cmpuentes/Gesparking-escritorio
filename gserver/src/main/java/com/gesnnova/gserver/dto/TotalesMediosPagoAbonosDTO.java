package com.gesnnova.gserver.dto;

public class TotalesMediosPagoAbonosDTO {

    private long efectivo;
    private long tarjeta;
    private long transferencia;

    public TotalesMediosPagoAbonosDTO(long efectivo, long tarjeta, long transferencia) {
        this.efectivo = efectivo;
        this.tarjeta = tarjeta;
        this.transferencia = transferencia;
    }

    public long getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(long efectivo) {
        this.efectivo = efectivo;
    }

    public long getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(long tarjeta) {
        this.tarjeta = tarjeta;
    }

    public long getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(long transferencia) {
        this.transferencia = transferencia;
    }
}
