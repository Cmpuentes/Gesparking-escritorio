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

        Optional<Empleado> empleadoOpt =
                empleadoRepository.findByLogin(request.getLogin());

        if (empleadoOpt.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("Usuario no encontrado");
            return response;
        }

        Empleado empleado = empleadoOpt.get();
        Integer idEmpresa = empleado.getEmpresa().getIdempresa();

        if (!empleado.getPassword().equals(request.getPassword())) {
            response.setSuccess(false);
            response.setMessage("Credenciales inválidas");
            return response;
        }

        if (!"Activo".equalsIgnoreCase(empleado.getEstado())) {
            response.setSuccess(false);
            response.setMessage("El usuario está inactivo");
            return response;
        }

        // ===============================
        // SESIÓN (POR EMPRESA)
        // ===============================
        Sesiones sesion = sesionRepository
                .findByIdempleadoAndIdEmpresa(
                        empleado.getIdempleado(), idEmpresa
                )
                .filter(s -> "activo".equalsIgnoreCase(s.getEstado()))
                .orElseGet(() -> {
                    sesionRepository.deleteByIdempleadoAndIdEmpresa(
                            empleado.getIdempleado(), idEmpresa
                    );

                    Sesiones s = new Sesiones();
                    s.setIdempleado(empleado.getIdempleado());
                    s.setIdEmpresa(idEmpresa);
                    s.setToken(generateToken());
                    s.setFecha_inicio(LocalDateTime.now());
                    s.setEstado("activo");
                    return s;
                });

        sesion.setExpiracion(LocalDateTime.now().plusHours(48));
        sesionRepository.save(sesion);

        // ===============================
        // TURNO (POR EMPRESA)
        // ===============================
        String nombreEmpleado =
                empleado.getNombres() + " " + empleado.getApellidos();

        InicioTurno inicioTurno = inicioTurnoRepository
                .findByEmpleadoAndIdEmpresaAndEstado(
                        nombreEmpleado, idEmpresa, "Activo"
                )
                .orElseGet(() -> {

                    Integer ultimo =
                            inicioTurnoRepository.obtenerUltimoNumeroTurnoPorEmpresa(idEmpresa);

                    int numeroTurno = (ultimo != null ? ultimo : 0) + 1;

                    InicioTurno t = new InicioTurno();
                    t.setEmpleado(nombreEmpleado);
                    t.setIdEmpresa(idEmpresa);
                    t.setNumeroTurno(numeroTurno);
                    t.setEstado("Activo");

                    // 🔥 FECHA GENERADA EN BACKEND
                    DateTimeFormatter fmt =
                            DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

                    t.setFechaInicio(LocalDateTime.now().format(fmt));

                    // 🔥 NOMBRE DEL TURNO GENERADO EN BACKEND
                    String fechaHoy = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    t.setTurno("Turno " + numeroTurno + " " + fechaHoy);

                    return inicioTurnoRepository.save(t);
                });

        // ===============================
        // RESPUESTA
        // ===============================
        response.setSuccess(true);
        response.setMessage("Login exitoso");
        response.setToken(sesion.getToken());
        response.setNombreCompleto(nombreEmpleado);
        response.setFecha_inicio(inicioTurno.getFechaInicio());
        response.setTurno(inicioTurno.getTurno());
        response.setNumero_turno(inicioTurno.getNumeroTurno());
        response.setIdEmpresa(idEmpresa);
        response.setNombreEmpresa(empleado.getEmpresa().getNombre());

        return response;
    }




    //CHECK SESSION PARA ESCRITORIO
    public LoginResponse checkSessionFull(String token) {

        LoginResponse response = new LoginResponse();

        Optional<Sesiones> sesionOpt = sesionRepository.findByToken(token);

        if (sesionOpt.isEmpty() || !"activo".equalsIgnoreCase(sesionOpt.get().getEstado())) {
            response.setSuccess(false);
            response.setMessage("Sesión inválida o expirada");
            return response;
        }

        Sesiones sesion = sesionOpt.get();

        Empleado empleado = empleadoRepository
                .findById(sesion.getIdempleado())
                .orElse(null);

        if (empleado == null) {
            response.setSuccess(false);
            response.setMessage("Empleado no encontrado para la sesión");
            return response;
        }

        Integer idEmpresa = sesion.getIdEmpresa();
        String nombreEmpleado =
                empleado.getNombres() + " " + empleado.getApellidos();

        // 🔥 TURNO SOLO DE ESA EMPRESA
        Optional<InicioTurno> turnoOpt =
                inicioTurnoRepository.findByEmpleadoAndIdEmpresaAndEstado(
                        nombreEmpleado,
                        idEmpresa,
                        "activo"
                );

        if (turnoOpt.isEmpty()) {
            response.setSuccess(false);
            response.setMessage("No hay turno activo para esta empresa");
            return response;
        }

        InicioTurno turno = turnoOpt.get();

        // 🎁 RESPUESTA COMPLETA
        response.setSuccess(true);
        response.setMessage("Sesión válida");
        response.setToken(sesion.getToken());
        response.setNombreCompleto(nombreEmpleado);
        response.setFecha_inicio(turno.getFechaInicio());
        response.setTurno(turno.getTurno());
        response.setNumero_turno(turno.getNumeroTurno());
        response.setIdEmpresa(idEmpresa);
        response.setNombreEmpresa(empleado.getEmpresa().getNombre());

        return response;
    }

}
