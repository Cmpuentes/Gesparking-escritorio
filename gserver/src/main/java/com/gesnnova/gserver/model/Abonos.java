package com.gesnnova.gserver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "abonos")
public class Abonos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idabonos;

    private String cliente;
    private String fecha;
    private int valor;
    private int efectivo;
    private int tarjeta;
    private int transferencia;
    private int total;
    private String turno;
    private String empleado;
    private String observaciones;

    @Column(name = "numero_turno")
    private String numeroturno;
    private int saldo;

    public Long getIdabonos() {
        return idabonos;
    }

    public void setIdabonos(Long idabonos) {
        this.idabonos = idabonos;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNumeroturno() {
        return numeroturno;
    }

    public void setNumeroturno(String numeroturno) {
        this.numeroturno = numeroturno;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
