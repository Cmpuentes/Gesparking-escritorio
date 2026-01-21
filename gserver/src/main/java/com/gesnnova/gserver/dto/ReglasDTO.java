package com.gesnnova.gserver.dto;

import com.gesnnova.gserver.model.TipoCobro;
import lombok.Data;

public class ReglasDTO {

    private Integer idreglas;

    private String tiposervicio;

    private TipoCobro tipocobro;

    private Boolean aplicagracia;

    private Integer maxhoras;

    private Integer minutogracia;

    private Integer minutoextra;

    private Integer idempresa;

    private String cobrobloques;

    public Integer getIdreglas() {
        return idreglas;
    }

    public void setIdreglas(Integer idreglas) {
        this.idreglas = idreglas;
    }

    public String getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(String tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public TipoCobro getTipocobro() {
        return tipocobro;
    }

    public void setTipocobro(TipoCobro tipocobro) {
        this.tipocobro = tipocobro;
    }

    public Boolean getAplicagracia() {
        return aplicagracia;
    }

    public void setAplicagracia(Boolean aplicagracia) {
        this.aplicagracia = aplicagracia;
    }

    public Integer getMaxhoras() {
        return maxhoras;
    }

    public void setMaxhoras(Integer maxhoras) {
        this.maxhoras = maxhoras;
    }

    public Integer getMinutogracia() {
        return minutogracia;
    }

    public void setMinutogracia(Integer minutogracia) {
        this.minutogracia = minutogracia;
    }

    public Integer getMinutoextra() {
        return minutoextra;
    }

    public void setMinutoextra(Integer minutoextra) {
        this.minutoextra = minutoextra;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public String getCobrobloques() {
        return cobrobloques;
    }

    public void setCobrobloques(String cobrobloques) {
        this.cobrobloques = cobrobloques;
    }
}
