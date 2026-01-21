package com.gesnnova.gserver.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inicio_turno")
public class InicioTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idturno;

    private String empleado;
    private String fecha_inicio;
    private String turno;
    @Column(name = "numero_turno")
    private int numeroTurno;
    private String estado;

    public Integer getIdturno() {
        return idturno;
    }

    public void setIdturno(Integer idturno) {
        this.idturno = idturno;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
