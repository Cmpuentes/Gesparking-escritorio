package com.gesnnova.gserver.service;

import com.gesnnova.gserver.model.TiempoCalculado;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TiempoService {

    public TiempoCalculado calcularTiempo(String fechaIngreso, String fechaSalida) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", Locale.ENGLISH);


//        // 👇 Diagnóstico
//        System.out.println("Analizando fecha de ingreso:");
//        for (char c : fechaIngreso.toCharArray()) {
//            System.out.println("'" + c + "' -> " + (int) c);
//        }
//
//        // 👇 Normaliza espacios invisibles
//        // Elimina todos los espacios no imprimibles
//        fechaIngreso = fechaIngreso.replaceAll("[\\u00A0]", " ").trim();
//        fechaSalida = fechaSalida.replaceAll("[\\u00A0]", " ").trim();

        LocalDateTime ingreso = LocalDateTime.parse(fechaIngreso, formatter);
        LocalDateTime salida = LocalDateTime.parse(fechaSalida, formatter);

        Duration duration = Duration.between(ingreso, salida);
        long totalMinutos = duration.toMinutes();

        if (totalMinutos <= 15) {
            return new TiempoCalculado(0, 0, 0); // tiempo de gracia
        }

        long horasCompletas = totalMinutos / 60;
        long minutosRestantes = totalMinutos % 60;

        long dias = horasCompletas / 24;
        long horas = horasCompletas % 24;

        return new TiempoCalculado((int) dias, (int) horas, (int) minutosRestantes);
    }
}
