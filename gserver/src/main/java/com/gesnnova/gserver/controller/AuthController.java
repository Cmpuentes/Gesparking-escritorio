package com.gesnnova.gserver.controller;


import com.gesnnova.gserver.dto.LoginReques;
import com.gesnnova.gserver.dto.LoginResponse;
import com.gesnnova.gserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite cualquier origen (útil para pruebas)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginReques request) {
        return authService.login(request);
    }

    @GetMapping("/check-session-full")
    public LoginResponse checkSessionFull(@RequestParam String token) {
        return authService.checkSessionFull(token);
    }

}
