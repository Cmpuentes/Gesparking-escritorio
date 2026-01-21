package com.gesnnova.gserver.controller;

import com.gesnnova.gserver.dto.EmpleadoLoginRequestDTO;
import com.gesnnova.gserver.dto.EmpleadoLoginResponseDTO;
import com.gesnnova.gserver.service.EmpleadoEscritorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/administrativos")
@CrossOrigin(origins = "*")
public class AdministrativoController {
    @Autowired
    private EmpleadoEscritorioService empleadoEscritorioService;

    @PostMapping("/login")
    public ResponseEntity<EmpleadoLoginResponseDTO> login(
            @RequestBody EmpleadoLoginRequestDTO request) {

        return empleadoEscritorioService.login(request.getLogin(), request.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

//    @Autowired
//    private EmpleadoEscritorioService empleadoEscritorioService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody EmpleadoLoginRequestDTO request) {
//
//        EmpleadoLoginDTO dto =
//                empleadoEscritorioService.login(
//                        request.getLogin(),
//                        request.getPassword()
//                );
//
//        if (dto != null) {
//            return ResponseEntity.ok(dto);
//        }
//
//        return ResponseEntity.status(401).body(false);
//    }
}

