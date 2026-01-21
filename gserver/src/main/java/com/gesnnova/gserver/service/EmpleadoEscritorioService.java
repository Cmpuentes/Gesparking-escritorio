package com.gesnnova.gserver.service;

import com.gesnnova.gserver.dto.EmpleadoAdminDTO;
import com.gesnnova.gserver.dto.EmpleadoLoginResponseDTO;
import com.gesnnova.gserver.model.Empleado;
import com.gesnnova.gserver.model.Empresa;
import com.gesnnova.gserver.repository.EmpleadoEscritorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoEscritorioService {

    @Autowired
    private EmpleadoEscritorioRepository empleadoEscritorioRepository;

    public List<Empleado> listarPorDocumento(String buscar, Integer idempresa) {
        if (buscar == null || buscar.isEmpty()) {
            return empleadoEscritorioRepository.findByEmpresa_Idempresa(idempresa);
        }
        return empleadoEscritorioRepository
                .findByEmpresa_IdempresaAndDocumentoContainingIgnoreCase(idempresa, buscar);
    }


    //Usado en el modulo administrativo
    public EmpleadoAdminDTO crearEmpleado(EmpleadoAdminDTO dto, Integer idempresa) {

        Empleado empleado = new Empleado();
        empleado.setNombres(dto.getNombres());
        empleado.setApellidos(dto.getApellidos());
        empleado.setTipodocumento(dto.getTipodocumento());
        empleado.setDocumento(dto.getDocumento());
        empleado.setLogin(dto.getLogin());
        empleado.setPassword(dto.getPassword());
        empleado.setEstado(dto.getEstado());
        empleado.setTelefono(dto.getTelefono());
        empleado.setEmail(dto.getEmail());
        empleado.setDireccion(dto.getDireccion());
        empleado.setPais(dto.getPais());
        empleado.setCiudad(dto.getCiudad());
        empleado.setEps(dto.getEps());
        empleado.setArl(dto.getArl());
        empleado.setAcceso(dto.getAcceso());

        Empresa empresa = new Empresa();
        empresa.setIdempresa(idempresa); // 🔥 CLAVE
        empleado.setEmpresa(empresa);

        Empleado guardado = empleadoEscritorioRepository.save(empleado);

        EmpleadoAdminDTO respuesta = new EmpleadoAdminDTO();
        respuesta.setIdempleado(guardado.getIdempleado());
        respuesta.setNombres(guardado.getNombres());
        respuesta.setApellidos(guardado.getApellidos());
        respuesta.setAcceso(guardado.getAcceso());

        return respuesta;
    }


    //Usado en el modulo administrativo
    public Empleado actualizarEmpleado(Integer idempleado,
                                       EmpleadoAdminDTO dto,
                                       Integer idempresa) {

        Empleado emp = empleadoEscritorioRepository
                .findByIdempleadoAndEmpresa_Idempresa(idempleado, idempresa)
                .orElseThrow(() ->
                        new RuntimeException("Empleado no pertenece a esta empresa"));

        emp.setNombres(dto.getNombres());
        emp.setApellidos(dto.getApellidos());
        emp.setTelefono(dto.getTelefono());
        emp.setDireccion(dto.getDireccion());
        emp.setEmail(dto.getEmail());
        emp.setPais(dto.getPais());
        emp.setCiudad(dto.getCiudad());
        emp.setAcceso(dto.getAcceso());
        emp.setLogin(dto.getLogin());
        emp.setPassword(dto.getPassword());
        emp.setEstado(dto.getEstado());
        emp.setEps(dto.getEps());
        emp.setArl(dto.getArl());

        return empleadoEscritorioRepository.save(emp);
    }


    //Usado en el modulo administrativo
    public void eliminarEmpleado(Integer idempleado, Integer idempresa) {
        Empleado emp = empleadoEscritorioRepository
                .findByIdempleadoAndEmpresa_Idempresa(idempleado, idempresa)
                .orElseThrow(() ->
                        new RuntimeException("Empleado no pertenece a esta empresa"));

        empleadoEscritorioRepository.delete(emp);
    }


    @Autowired
    private EmpleadoEscritorioRepository repo;

    public Optional<EmpleadoLoginResponseDTO> login(String login, String password) {
        return repo.loginYObtenerEmpresa(login, password);


    }
}
