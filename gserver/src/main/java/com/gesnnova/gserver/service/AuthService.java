package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.LoginReques;
import com.gesnnova.gserver.dto.LoginResponse;
import com.gesnnova.gserver.model.Empleado;
import com.gesnnova.gserver.model.InicioTurno;
import com.gesnnova.gserver.model.Sesiones;
import com.gesnnova.gserver.repository.EmpleadoRepository;
import com.gesnnova.gserver.repository.InicioTurnoRepository;
import com.gesnnova.gserver.repository.SesionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private InicioTurnoRepository inicioTurnoRepository;

    // Genera un token alfanumérico aleatorio
    private String generateToken() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder token = new StringBuilder(16);
        Random rnd = new Random();
        for (int i = 0; i < 16; i++) {
            token.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return token.toString();
    }

    @Transactional
    public LoginResponse login(LoginReques request) {
        LoginResponse response = new LoginResponse();

        // 1. Buscar empleado por login
        var empleadoOpt = empleadoRepository.findByLogin(request.getLogin());

        if (empleadoOpt.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Usuario no encontrado");
            return response;
        }

        Empleado empleado = empleadoOpt.get();

        // 2. Validar contraseña
        if (!empleado.getPassword().equals(request.getPassword())) {
            response.setSuccess(false);
            response.setMessage("Credenciales inválidas");
            return response;
        }

        // 3. Validar estado del empleado
        if (!"Activo".equalsIgnoreCase(empleado.getEstado())) {
            response.setSuccess(false);
            response.setMessage("El usuario está inactivo");
            return response;
        }

        // 4. Verificar sesión existente
        var sesionOpt = sesionRepository.findByIdempleado(empleado.getIdempleado());
        Sesiones sesion;

        if (sesionOpt.isPresent() && "activo".equalsIgnoreCase(sesionOpt.get().getEstado())) {
            // Reutilizar sesión activa
            sesion = sesionOpt.get();
            sesion.setExpiracion(LocalDateTime.now().plusHours(48));
            sesionRepository.save(sesion);
        } else {
            // Eliminar sesiones viejas y crear nueva
            sesionRepository.deleteByIdempleado(empleado.getIdempleado());

            sesion = new Sesiones();
            sesion.setIdempleado(empleado.getIdempleado());
            sesion.setToken(generateToken());
            sesion.setFecha_inicio(LocalDateTime.now());
            sesion.setExpiracion(LocalDateTime.now().plusHours(48));
            sesion.setEstado("activo");

            sesion = sesionRepository.save(sesion);
        }

        // 5. Buscar si ya hay turno activo para este empleado
        Optional<InicioTurno> turnoActivoOpt =
                inicioTurnoRepository.findByEmpleadoAndEstado(
                        empleado.getNombres() + " " + empleado.getApellidos(),
                        "Activo"
                );

        InicioTurno inicioTurno;

        if (turnoActivoOpt.isPresent()) {
            // ✅ Reutilizar turno activo
            inicioTurno = turnoActivoOpt.get();
        } else {
            // ❌ No hay turno activo → crear uno nuevo
            Integer ultimoTurno = inicioTurnoRepository.obtenerUltimoNumeroTurno();
            int numeroTurno = (ultimoTurno != null ? ultimoTurno : 0) + 1;

            inicioTurno = new InicioTurno();
            inicioTurno.setEmpleado(empleado.getNombres() + " " + empleado.getApellidos());
            inicioTurno.setFecha_inicio(request.getFecha_inicio());

            String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            inicioTurno.setTurno(request.getTurno() + " " + fechaHoy);
            inicioTurno.setNumeroTurno(numeroTurno);
            inicioTurno.setEstado("Activo");

            inicioTurnoRepository.save(inicioTurno);
        }

        // 6. Preparar respuesta
        response.setSuccess(true);
        response.setMessage("Login exitoso");
        response.setToken(sesion.getToken());
        response.setNombreCompleto(empleado.getNombres() + " " + empleado.getApellidos());
        response.setFecha_inicio(inicioTurno.getFecha_inicio());
        response.setTurno(inicioTurno.getTurno());
        response.setNumero_turno(inicioTurno.getNumeroTurno());

        return response;
    }

    //CHECK SESSION PARA ESCRITORIO
    public LoginResponse checkSessionFull(String token) {
        LoginResponse response = new LoginResponse();

        var sesionOpt = sesionRepository.findByToken(token);
        if (sesionOpt.isEmpty() || !"activo".equalsIgnoreCase(sesionOpt.get().getEstado())) {
            response.setSuccess(false);
            response.setMessage("Sesión inválida o expirada");
            return response;
        }

        Sesiones sesion = sesionOpt.get();
        Empleado empleado = empleadoRepository.findById(sesion.getIdempleado()).orElse(null);

        if (empleado == null) {
            response.setSuccess(false);
            response.setMessage("Empleado no encontrado para la sesión");
            return response;
        }

        // Buscar turno activo del empleado
        Optional<InicioTurno> turnoOpt = inicioTurnoRepository
                .findByEmpleadoAndEstado(empleado.getNombres() + " " + empleado.getApellidos(), "Activo");

        if (turnoOpt.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("No hay turno activo para este empleado");
            return response;
        }

        InicioTurno turno = turnoOpt.get();

        // Construir respuesta completa
        response.setSuccess(true);
        response.setMessage("Sesión válida");
        response.setToken(sesion.getToken());
        response.setNombreCompleto(empleado.getNombres() + " " + empleado.getApellidos());
        response.setFecha_inicio(turno.getFecha_inicio());
        response.setTurno(turno.getTurno());
        response.setNumero_turno(turno.getNumeroTurno());

        return response;
    }
}
