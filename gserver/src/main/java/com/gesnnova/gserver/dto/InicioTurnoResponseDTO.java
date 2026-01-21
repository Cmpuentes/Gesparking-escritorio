package com.gesnnova.gserver.dto;

public class InicioTurnoResponseDTO {

    private Integer idturno;
    private String empleado;
    private String fechainicio;
    private String turno;
    private int numeroturno;
    private String estado;

    public InicioTurnoResponseDTO() {
    }

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

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getNumeroturno() {
        return numeroturno;
    }

    public void setNumeroturno(int numeroturno) {
        this.numeroturno = numeroturno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
