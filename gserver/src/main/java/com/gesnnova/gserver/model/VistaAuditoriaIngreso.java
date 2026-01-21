package com.gesnnova.gserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable // marca que Hibernate no intentará escribir en esta entidad
@Table(name = "vista_auditoria_ingreso")
public class VistaAuditoriaIngreso {

    @Id
    @Column(name = "id_auditoria")
    private Integer idAuditoria;

    @Column(name = "operacion")
    private String operacion;

    @Column(name = "idingreso")
    private Integer idIngreso;

    @Column(name = "placa_anterior")
    private String placaAnterior;

    @Column(name = "tipovehiculo_anterior")
    private String tipoVehiculoAnterior;

    @Column(name = "tiposervicio_anterior")
    private String tipoServicioAnterior;

    @Column(name = "estado_anterior")
    private String estadoAnterior;

    @Column(name = "turno")
    private String turno;

    @Column(name = "empleado")
    private String empleado;

    @Column(name = "fechaingreso_anterior")
    private String fechaIngresoAnterior;

    @Column(name = "placa_nueva")
    private String placaNueva;

    @Column(name = "tipovehiculo_nuevo")
    private String tipoVehiculoNuevo;

    @Column(name = "tiposervicio_nuevo")
    private String tipoServicioNuevo;

    @Column(name = "estado_nuevo")
    private String estadoNuevo;

    @Column(name = "fechaingreso_nuevo")
    private String fechaIngresoNuevo;

    @Column(name = "observaciones_nueva")
    private String observacionesNueva;

    @Column(name = "usuario_app")
    private String usuarioApp;

    // Si en BD fecha es TIMESTAMP, puedes mapearlo a LocalDateTime:
    @Column(name = "fecha")
    private LocalDateTime fecha;

    public VistaAuditoriaIngreso() {
    }

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

    public Integer getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }

    public String getPlacaAnterior() {
        return placaAnterior;
    }

    public void setPlacaAnterior(String placaAnterior) {
        this.placaAnterior = placaAnterior;
    }

    public String getTipoVehiculoAnterior() {
        return tipoVehiculoAnterior;
    }

    public void setTipoVehiculoAnterior(String tipoVehiculoAnterior) {
        this.tipoVehiculoAnterior = tipoVehiculoAnterior;
    }

    public String getTipoServicioAnterior() {
        return tipoServicioAnterior;
    }

    public void setTipoServicioAnterior(String tipoServicioAnterior) {
        this.tipoServicioAnterior = tipoServicioAnterior;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
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

    public String getFechaIngresoAnterior() {
        return fechaIngresoAnterior;
    }

    public void setFechaIngresoAnterior(String fechaIngresoAnterior) {
        this.fechaIngresoAnterior = fechaIngresoAnterior;
    }

    public String getPlacaNueva() {
        return placaNueva;
    }

    public void setPlacaNueva(String placaNueva) {
        this.placaNueva = placaNueva;
    }

    public String getTipoVehiculoNuevo() {
        return tipoVehiculoNuevo;
    }

    public void setTipoVehiculoNuevo(String tipoVehiculoNuevo) {
        this.tipoVehiculoNuevo = tipoVehiculoNuevo;
    }

    public String getTipoServicioNuevo() {
        return tipoServicioNuevo;
    }

    public void setTipoServicioNuevo(String tipoServicioNuevo) {
        this.tipoServicioNuevo = tipoServicioNuevo;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getFechaIngresoNuevo() {
        return fechaIngresoNuevo;
    }

    public void setFechaIngresoNuevo(String fechaIngresoNuevo) {
        this.fechaIngresoNuevo = fechaIngresoNuevo;
    }

    public String getObservacionesNueva() {
        return observacionesNueva;
    }

    public void setObservacionesNueva(String observacionesNueva) {
        this.observacionesNueva = observacionesNueva;
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
