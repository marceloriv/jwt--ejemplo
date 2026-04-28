package cl.duoc.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.jwt.dto.LoginRequest;
import cl.duoc.jwt.dto.LoginResponse;
import cl.duoc.jwt.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService service;

    @GetMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return LoginResponse(service.generarToken(request.username()));

        
    }

}
