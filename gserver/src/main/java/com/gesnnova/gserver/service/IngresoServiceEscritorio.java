package com.gesnnova.gserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gesnnova.gserver.dto.ConteoActivoDTO;
import com.gesnnova.gserver.dto.IngresdoEscritorioDTO;
import com.gesnnova.gserver.dto.IngresoEscritorioDTO;
import com.gesnnova.gserver.dto.SalidaConsultaDTO;
import com.gesnnova.gserver.model.AuditoriaIngreso;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.repository.AuditoriaIngresoRepository;
import com.gesnnova.gserver.repository.IngresoRepositoryEscritorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IngresoServiceEscritorio {

    @Autowired
    private IngresoRepositoryEscritorio ingresoRepositoryEscritorio;

    @Autowired
    private AuditoriaIngresoRepository auditoriaIngresoRepository;

    @Autowired
    private ObjectMapper objectMapper; // de Jackson, o puedes usar Gson

    public List<IngresoEscritorioDTO> obtenerIngresosActivos(String buscar) {
        List<Ingreso> ingresos = ingresoRepositoryEscritorio.buscarIngresosActivos(buscar);
        List<IngresoEscritorioDTO> lista = new ArrayList<>();

        for (Ingreso i : ingresos) {
            IngresoEscritorioDTO dto = new IngresoEscritorioDTO();
            dto.setIdingreso(i.getIdingreso());
            dto.setTurno(i.getTurno());
            dto.setNumeroturno(i.getNumeroturno());
            dto.setEmpleado(i.getEmpleado());
            dto.setPlaca(i.getPlaca());
            dto.setFechaingreso(i.getFechaingreso());
            dto.setTipovehiculo(i.getTipovehiculo());
            dto.setTiposervicio(i.getTiposervicio());
            dto.setCliente(i.getCliente());
            dto.setZona(i.getZona());
            dto.setObservaciones(i.getObservaciones());
            dto.setEstado(i.getEstado());
            lista.add(dto);
        }

        return lista;
    }

    public boolean verificarPlacaActiva(String placa) {
        return ingresoRepositoryEscritorio.existsByPlacaAndEstado(placa, "Activo");
    }

    public Ingreso registrarIngreso(IngresdoEscritorioDTO dto) {
        // Validar si la placa ya tiene un ingreso activo
        Optional<Ingreso> existente = ingresoRepositoryEscritorio.findByPlacaAndEstado(dto.getPlaca(), "Activo");
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un ingreso activo para la placa " + dto.getPlaca());
        }

        Ingreso ingreso = new Ingreso();

        ingreso.setTurno(dto.getTurno());
        ingreso.setEmpleado(dto.getEmpleado());
        ingreso.setPlaca(dto.getPlaca());
        ingreso.setFechaingreso(dto.getFechaingreso());
        ingreso.setTipovehiculo(dto.getTipovehiculo());
        ingreso.setTiposervicio(dto.getTiposervicio());
        ingreso.setCliente(dto.getCliente());
        ingreso.setZona(dto.getZona());
        ingreso.setObservaciones(dto.getObservaciones());
        ingreso.setEstado(dto.getEstado());
        ingreso.setNumeroturno(dto.getNumeroturno());

        return ingresoRepositoryEscritorio.save(ingreso);
    }

    public void actualizarIngresoConAuditoria(Integer id, IngresdoEscritorioDTO dto, String usuarioApp) {
        Ingreso ingreso = ingresoRepositoryEscritorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado con ID: " + id));

        try {
            // 🔹 1. Convertir los datos actuales (antes de actualizar) a JSON
            String datosAnteriores = objectMapper.writeValueAsString(ingreso);

            // 🔹 2. Actualizar los campos desde el DTO
            ingreso.setTurno(dto.getTurno());
            ingreso.setEmpleado(dto.getEmpleado());
            ingreso.setPlaca(dto.getPlaca());
            ingreso.setFechaingreso(dto.getFechaingreso());
            ingreso.setTipovehiculo(dto.getTipovehiculo());
            ingreso.setTiposervicio(dto.getTiposervicio());
            ingreso.setCliente(dto.getCliente());
            ingreso.setZona(dto.getZona());
            ingreso.setObservaciones(dto.getObservaciones()); // ⭐ esta es la importante
            ingreso.setObservaciones(dto.getObservaciones());
            ingreso.setEstado(dto.getEstado());

            // 🔹 3. Convertir los nuevos datos a JSON
            String datosNuevos = objectMapper.writeValueAsString(ingreso);

            // 🔹 4. Guardar el ingreso actualizado
            ingresoRepositoryEscritorio.save(ingreso);

            // 🔹 5. Crear y guardar registro de auditoría
            AuditoriaIngreso auditoria = new AuditoriaIngreso();
            auditoria.setOperacion(AuditoriaIngreso.Operacion.UPDATE);
            auditoria.setIdIngreso(ingreso.getIdingreso());
            auditoria.setDatosAnteriores(datosAnteriores);
            auditoria.setDatosNuevos(datosNuevos);
            auditoria.setUsuarioApp(usuarioApp);
            auditoria.setObservaciones(dto.getObservaciones()); // ⭐⭐ aquí guardamos las observaciones
            auditoria.setIdEmpresa(0); // por ahora 0 o null

            auditoriaIngresoRepository.save(auditoria);

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar auditoría de actualización: " + e.getMessage());
        }
    }

    public void eliminarIngresoConAuditoria(Integer id, String usuarioApp, String observaciones) {
        Ingreso ingreso = ingresoRepositoryEscritorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado con ID: " + id));

        try {
            // 🔹 Convertir los datos actuales a JSON (datos_anteriores)
            String datosAnteriores = objectMapper.writeValueAsString(ingreso);

            // 🔹 Cambiar el estado a "Eliminado"
            ingreso.setEstado("Eliminado");

            // 🔹 Convertir el nuevo estado a JSON (datos_nuevos)
            String datosNuevos = objectMapper.writeValueAsString(ingreso);

            // 🔹 Guardar el ingreso actualizado (ya con estado "Eliminado")
            ingresoRepositoryEscritorio.save(ingreso);

            // 🔹 Crear registro en auditoría
            AuditoriaIngreso auditoria = new AuditoriaIngreso();
            auditoria.setOperacion(AuditoriaIngreso.Operacion.DELETE);
            auditoria.setIdIngreso(ingreso.getIdingreso());
            auditoria.setDatosAnteriores(datosAnteriores);
            auditoria.setDatosNuevos(datosNuevos);
            auditoria.setUsuarioApp(usuarioApp);
            auditoria.setIdEmpresa(0); // por ahora 0 o null
            auditoria.setObservaciones(observaciones);

            auditoriaIngresoRepository.save(auditoria);

        } catch (Exception e) {
            throw new RuntimeException("Error al registrar auditoría de eliminación: " + e.getMessage());
        }
    }

    public List<Ingreso> obtenerTodosLosIngresos() {
        return ingresoRepositoryEscritorio.findByEstadoOrderByIdingresoDesc("Activo");
    }

    //Obtener placas activas
    public List<String> obtenerPlacasActivas() {
        return ingresoRepositoryEscritorio.findPlacasActivas();
    }

    public SalidaConsultaDTO consultarIngresoPorPlaca(String placa) {
        Optional<Ingreso> ingresoOpt = ingresoRepositoryEscritorio.findByPlacaAndEstado(placa, "Activo");

        if (ingresoOpt.isPresent()) {
            Ingreso ingreso = ingresoOpt.get();
            SalidaConsultaDTO dto = new SalidaConsultaDTO();

            dto.setIdingreso(ingreso.getIdingreso().intValue());
            dto.setFechaingreso(ingreso.getFechaingreso());
            dto.setTipovehiculo(ingreso.getTipovehiculo());
            dto.setTiposervicio(ingreso.getTiposervicio());
            dto.setCliente(ingreso.getCliente());
            dto.setZona(ingreso.getZona());
            dto.setNumeroturno(ingreso.getNumeroturno());
            dto.setEmpleado(ingreso.getEmpleado());

            return dto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró un ingreso activo para la placa: " + placa);
        }
    }

    //Función para contar los vehículos activos en el parqueadero
    public ConteoActivoDTO contarEstadoActivo() {
        return ingresoRepositoryEscritorio.contarEstadoActivo();
    }
}
