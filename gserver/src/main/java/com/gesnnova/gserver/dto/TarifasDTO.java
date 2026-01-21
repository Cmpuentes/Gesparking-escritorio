package com.gesnnova.gserver.dto;

public class TarifasDTO {

    private int precio12h;
    private int preciohoras;
    private int descuentorecibo;

    public TarifasDTO(int precio12h, int preciohoras, int descuentorecibo) {
        this.precio12h = precio12h;
        this.preciohoras = preciohoras;
        this.descuentorecibo = descuentorecibo;
    }

    public int getPrecio12h() {
        return precio12h;
    }

    public void setPrecio12h(int precio12h) {
        this.precio12h = precio12h;
    }

    public int getPreciohoras() {
        return preciohoras;
    }

    public void setPreciohoras(int preciohoras) {
        this.preciohoras = preciohoras;
    }

    public int getDescuentorecibo() {
        return descuentorecibo;
    }

    public void setDescuentorecibo(int descuentorecibo) {
        this.descuentorecibo = descuentorecibo;
    }
}
