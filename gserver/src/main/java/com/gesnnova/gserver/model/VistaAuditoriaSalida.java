package com.gesnnova.gserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "vista_auditoria_salida")
public class VistaAuditoriaSalida {

    @Id
    @Column(name = "id_auditoria")
    private Integer idAuditoria;

    @Column(name = "operacion")
    private String operacion;

    // --- Placa ---
    @Column(name = "placa_anterior")
    private String placaAnterior;

    @Column(name = "placa_nueva")
    private String placaNueva;

    // --- Valores económicos ---
    @Column(name = "valor_anterior")
    private String valorAnterior;

    @Column(name = "valor_nuevo")
    private String valorNuevo;

    @Column(name = "efectivo_anterior")
    private String efectivoAnterior;

    @Column(name = "efectivo_nuevo")
    private String efectivoNuevo;

    @Column(name = "tarjeta_anterior")
    private String tarjetaAnterior;

    @Column(name = "tarjeta_nueva")
    private String tarjetaNueva;

    @Column(name = "transferencia_anterior")
    private String transferenciaAnterior;

    @Column(name = "transferencia_nueva")
    private String transferenciaNueva;

    @Column(name = "total_anterior")
    private String totalAnterior;

    @Column(name = "total_nuevo")
    private String totalNuevo;

    // --- Empleado y turno ---
    @Column(name = "empleado_salida")
    private String empleadoSalida;

    @Column(name = "turno")
    private String turno;

    // --- Auditoría ---
    @Column(name = "usuario_app")
    private String usuarioApp;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public Integer getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getPlacaAnterior() {
        return placaAnterior;
    }

    public void setPlacaAnterior(String placaAnterior) {
        this.placaAnterior = placaAnterior;
    }

    public String getPlacaNueva() {
        return placaNueva;
    }

    public void setPlacaNueva(String placaNueva) {
        this.placaNueva = placaNueva;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public String getEfectivoAnterior() {
        return efectivoAnterior;
    }

    public void setEfectivoAnterior(String efectivoAnterior) {
        this.efectivoAnterior = efectivoAnterior;
    }

    public String getEfectivoNuevo() {
        return efectivoNuevo;
    }

    public void setEfectivoNuevo(String efectivoNuevo) {
        this.efectivoNuevo = efectivoNuevo;
    }

    public String getTarjetaAnterior() {
        return tarjetaAnterior;
    }

    public void setTarjetaAnterior(String tarjetaAnterior) {
        this.tarjetaAnterior = tarjetaAnterior;
    }

    public String getTarjetaNueva() {
        return tarjetaNueva;
    }

    public void setTarjetaNueva(String tarjetaNueva) {
        this.tarjetaNueva = tarjetaNueva;
    }

    public String getTransferenciaAnterior() {
        return transferenciaAnterior;
    }

    public void setTransferenciaAnterior(String transferenciaAnterior) {
        this.transferenciaAnterior = transferenciaAnterior;
    }

    public String getTransferenciaNueva() {
        return transferenciaNueva;
    }

    public void setTransferenciaNueva(String transferenciaNueva) {
        this.transferenciaNueva = transferenciaNueva;
    }

    public String getTotalAnterior() {
        return totalAnterior;
    }

    public void setTotalAnterior(String totalAnterior) {
        this.totalAnterior = totalAnterior;
    }

    public String getTotalNuevo() {
        return totalNuevo;
    }

    public void setTotalNuevo(String totalNuevo) {
        this.totalNuevo = totalNuevo;
    }

    public String getEmpleadoSalida() {
        return empleadoSalida;
    }

    public void setEmpleadoSalida(String empleadoSalida) {
        this.empleadoSalida = empleadoSalida;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getUsuarioApp() {
        return usuarioApp;
    }

    public void setUsuarioApp(String usuarioApp) {
        this.usuarioApp = usuarioApp;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
