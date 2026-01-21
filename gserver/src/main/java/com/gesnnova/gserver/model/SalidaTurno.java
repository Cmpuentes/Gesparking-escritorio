package com.gesnnova.gserver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "salida_turno")
public class SalidaTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idfinturno;

    private String turno;

    @Column(name = "numero_turno")
    private String numeroturno;

    private String empleado;
    private String fechaingreso;
    private String fechasalida;
    private String recibos;

    @Column(name = "total_vehiculos")
    private int totalvehiculos;

    private int base;
    private int efectivo;
    private int tarjeta;
    private int transferencia;

    @Column(name = "otros_ingresos")
    private int otrosingresos;

    @Column(name = "efectivo_liquido")
    private int efectivoliquido;

    @Column(name = "total_recaudado")
    private int totalrecaudado;

    private String estado;
    private String observaciones;

    @Column(name = "total_abonos")
    private int totalabonos;

    @Column(name = "abono_efectivo")
    private Integer abonoEfectivo;

    @Column(name = "abono_tarjeta")
    private Integer abonoTarjeta;

    @Column(name = "abono_transferencia")
    private Integer abonoTransferencia;


    public Integer getIdfinturno() {
        return idfinturno;
    }

    public void setIdfinturno(Integer idfinturno) {
        this.idfinturno = idfinturno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getNumeroturno() {
        return numeroturno;
    }

    public void setNumeroturno(String numeroturno) {
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

    public String getRecibos() {
        return recibos;
    }

    public void setRecibos(String recibos) {
        this.recibos = recibos;
    }

    public int getTotalvehiculos() {
        return totalvehiculos;
    }

    public void setTotalvehiculos(int totalvehiculos) {
        this.totalvehiculos = totalvehiculos;
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

    public int getOtrosingresos() {
        return otrosingresos;
    }

    public void setOtrosingresos(int otrosingresos) {
        this.otrosingresos = otrosingresos;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getTotalabonos() {
        return totalabonos;
    }

    public void setTotalabonos(int totalabonos) {
        this.totalabonos = totalabonos;
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
