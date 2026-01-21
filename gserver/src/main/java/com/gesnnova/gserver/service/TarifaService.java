package com.gesnnova.gserver.service;

import com.gesnnova.gserver.model.TiempoCalculado;

public class TarifaService {

    public int calcularCosto(
            TiempoCalculado tiempo,
            String tipoServicio,
            int precioHora,
            int precio12h
    ) {
        long dias = tiempo.getDias();
        long horas = tiempo.getHoras();
        long minutos = tiempo.getMinutos();

        int costoDia = 0;
        int costoHoras = 0;

        if (minutos >= 15) {
            horas++; // redondeo por minutos
        }

        if ("GENERAL".equalsIgnoreCase(tipoServicio)) {

            if (dias > 0) {
                costoDia += dias * (precio12h * 2);
            }

            if (horas <= 3) {
                costoHoras = (int) (horas * precioHora);
            } else if (horas <= 12) {
                costoDia += precio12h;
            } else if (horas <= 15) {
                costoDia += precio12h;
                int horasExtra = (int) (horas - 12);
                costoHoras += horasExtra * precioHora;
            } else {
                costoDia += 2 * precio12h;
            }

        } else if ("PREPAGO".equalsIgnoreCase(tipoServicio)) {
            costoDia += dias * precio12h; // tarifa diaria
        }

        return costoDia + costoHoras;
    }
}
