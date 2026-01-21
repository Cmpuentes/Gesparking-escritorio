package com.gesnnova.gserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tarifas")
public class Tarifas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtarifas;

    private String tipovehiculo;
    private String tiposervicio;
    private int precio12h;
    private int descuentorecibo;
    private int preciohoras;

    @Column(nullable = false)
    private Integer idempresa;

    public Integer getIdtarifas() {
        return idtarifas;
    }

    public void setIdtarifas(Integer idtarifas) {
        this.idtarifas = idtarifas;
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

    public int getPrecio12h() {
        return precio12h;
    }

    public void setPrecio12h(int precio12h) {
        this.precio12h = precio12h;
    }

    public int getDescuentorecibo() {
        return descuentorecibo;
    }

    public void setDescuentorecibo(int descuentorecibo) {
        this.descuentorecibo = descuentorecibo;
    }

    public int getPreciohoras() {
        return preciohoras;
    }

    public void setPreciohoras(int preciohoras) {
        this.preciohoras = preciohoras;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }
}
