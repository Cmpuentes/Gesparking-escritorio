package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.ConteoActivoDTO;
import com.gesnnova.gserver.dto.IngresdoEscritorioDTO;
import com.gesnnova.gserver.dto.IngresoEscritorioDTO;
import com.gesnnova.gserver.dto.SalidaConsultaDTO;
import com.gesnnova.gserver.model.Ingreso;
import com.gesnnova.gserver.repository.IngresoRepositoryEscritorio;
import com.gesnnova.gserver.service.IngresoServiceEscritorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos/escritorio")
@CrossOrigin(origins = "*")
public class IngresoControllerEscritorio {

    @Autowired
    private IngresoServiceEscritorio ingresoServiceEscritorio;

    @Autowired
    private IngresoRepositoryEscritorio ingresoRepositoryEscritorio;

    @PostMapping
    public Ingreso registrarIngreso(@RequestBody IngresdoEscritorioDTO dto) {
        return ingresoServiceEscritorio.registrarIngreso(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarIngreso(
            @PathVariable Integer id,
            @RequestBody IngresdoEscritorioDTO dto,
            @RequestParam("usuario") String usuarioApp) {

        ingresoServiceEscritorio.actualizarIngresoConAuditoria(id, dto, usuarioApp);
        return ResponseEntity.ok("Ingreso actualizado y registrado en auditoría.");
    }

    @GetMapping("/activos")
    public List<IngresoEscritorioDTO> listarIngresosActivos(@RequestParam(defaultValue = "") String buscar) {
        return ingresoServiceEscritorio.obtenerIngresosActivos(buscar);
    }

    @GetMapping("/ingresos/placa-activa")
    public ResponseEntity<Boolean> verificarPlacaActiva(@RequestParam String placa) {
        boolean existe = ingresoServiceEscritorio.verificarPlacaActiva(placa);
        return ResponseEntity.ok(existe);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarIngreso(
            @PathVariable Integer id,
            @RequestParam("usuario") String usuarioApp,
            @RequestParam("observaciones") String observaciones) {

        ingresoServiceEscritorio.eliminarIngresoConAuditoria(id, usuarioApp, observaciones);
        return ResponseEntity.ok("Ingreso marcado como eliminado y registrado en auditoría.");
    }

    //LISTAR INGRESOS ACTIVOS
    @GetMapping
    public List<Ingreso> listarIngresos() {
        return ingresoServiceEscritorio.obtenerTodosLosIngresos();
    }

    //Mostrar placas activas
    @GetMapping("/placas-activas")
    public ResponseEntity<List<String>> obtenerPlacasActivas() {
        List<String> placas = ingresoServiceEscritorio.obtenerPlacasActivas();
        return ResponseEntity.ok(placas);
    }

    @GetMapping("/detalle/{placa}")
    public ResponseEntity<SalidaConsultaDTO> obtenerDetallePorPlaca(@PathVariable String placa) {
        SalidaConsultaDTO dto = ingresoServiceEscritorio.consultarIngresoPorPlaca(placa);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/arqueo")
    public ResponseEntity<List<Ingreso>> obtenerIngresosActivos() {
        List<Ingreso> activos = ingresoRepositoryEscritorio.findByEstadoOrderByPlacaAsc("Activo");

        if (activos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 si no hay activos
        }

        return ResponseEntity.ok(activos); // 200 con JSON si hay registros
    }

    @GetMapping("/contar-activos")
    public ResponseEntity<ConteoActivoDTO> contarEstadoActivo() {
        ConteoActivoDTO conteo = ingresoServiceEscritorio.contarEstadoActivo();
        return ResponseEntity.ok(conteo);
    }
}
