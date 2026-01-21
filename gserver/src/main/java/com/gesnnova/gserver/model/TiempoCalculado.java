package com.gesnnova.gserver.model;

public class TiempoCalculado {

    private int dias;
    private int horas;
    private int minutos;

    public TiempoCalculado(int dias, int horas, int minutos) {
        this.dias = dias;
        this.horas = horas;
        this.minutos = minutos;
    }

    public int getDias() {
        return dias;
    }

    public int getHoras() {
        return horas;
    }

    public int getMinutos() {
        return minutos;
    }

    @Override
    public String toString() {
        return dias + " días, " + horas + " horas, " + minutos + " minutos";
    }
}
