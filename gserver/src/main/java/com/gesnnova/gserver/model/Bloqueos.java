package com.gesnnova.gserver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bloqueos")
public class Bloqueos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idbloqueo;

    @Column(name = "tipo_bloqueo")
    private String tipobloqueo;
    private String codigo;

    public Integer getIdbloqueo() {
        return idbloqueo;
    }

    public void setIdbloqueo(Integer idbloqueo) {
        this.idbloqueo = idbloqueo;
    }

    public String getTipobloqueo() {
        return tipobloqueo;
    }

    public void setTipobloqueo(String tipobloqueo) {
        this.tipobloqueo = tipobloqueo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}


