package com.gesnnova.gserver.service;

public class DescuntoService {

    public int calcularValorConDescuento(int dias, int horas, int  minutos, int precio1h, int descuentoRecibo){

        // Inicializamos los costos por días y horas
        int costoPorDias = 0;
        int costoPorHoras = 0;

        // Calcular el costo por días
        if(dias > 0){
            costoPorDias = (descuentoRecibo * 2) * dias;
        }

        // Calcular el costo por horas
        if(horas > 0 || minutos > 0){
            // Si los minutos son 15 o más, sumamos una hora adicional
            if(minutos >= 15){
                horas++;
                minutos = 0; // Reseteamos los minutos a 0 después de sumar la hora
            }
            // Si las horas superan las 24 horas, agregamos días adicionales
            if(horas >= 24){
                int diasExtras = horas / 24;  // Calculamos los días extras
                dias += diasExtras;  // Agregamos los días extras
                horas = horas % 24;  // Reajustamos las horas que quedan después de agregar los días
            }
            costoPorHoras = precio1h * horas; // Solo se cobra por las horas ajustadas
//                System.out.println("costo horas: " + costoPorHoras);
        }
        // Ahora, si las horas superan el costo por un día, se ajusta el costo
        if(costoPorHoras > descuentoRecibo){
            costoPorHoras = descuentoRecibo;
        }
        // Calcular el costo total sin descuento
        int costoTotal = (costoPorDias + costoPorHoras);

        return costoTotal;
    }
}
