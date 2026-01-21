package com.gesnnova.gserver.dto;

import com.gesnnova.gserver.model.Salida;

public class SalidaResponseDTO {

    private int numfactura;
    private String placa;
    private String fechaentrada;
    private String fechasalida;
    private String tipovehiculo;
    private String tiposervicio;
    private int dias;
    private int horas;
    private int minutos;
    private int valor;
    private int numerorecibo;
    private int descuento;
    private int total;
    private int efectivo;
    private int tarjeta;
    private int transferencia;

    public SalidaResponseDTO() {
    }

    // Constructor
    public SalidaResponseDTO(Salida salida) {
        this.numfactura = salida.getNumfactura();
        this.placa = salida.getPlaca();
        this.fechaentrada = salida.getFechaentrada();
        this.fechasalida = salida.getFechasalida();
        this.tipovehiculo = salida.getTipovehiculo();
        this.tiposervicio = salida.getTiposervicio();
        this.dias = salida.getDias();
        this.horas = salida.getHoras();
        this.minutos = salida.getMinutos();
        this.valor = salida.getValor();
        this.numerorecibo = salida.getNumerorecibo();
        this.descuento = salida.getDescuento();
        this.total = salida.getTotal();
        this.efectivo = salida.getEfectivo();
        this.tarjeta = salida.getTarjeta();
        this.transferencia = salida.getTransferencia();
    }

    public int getNumfactura() {
        return numfactura;
    }

    public void setNumfactura(int numfactura) {
        this.numfactura = numfactura;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFechaentrada() {
        return fechaentrada;
    }

    public void setFechaentrada(String fechaentrada) {
        this.fechaentrada = fechaentrada;
    }

    public String getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(String fechasalida) {
        this.fechasalida = fechasalida;
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(String tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }

    public String getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getNumerorecibo() {
        return numerorecibo;
    }

    public void setNumerorecibo(int numerorecibo) {
        this.numerorecibo = numerorecibo;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
