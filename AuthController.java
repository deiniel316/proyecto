
package com.bienestar.backend;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClienteRepository clienteRepository;

    public AuthController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        // buscar cliente por email y validar password (en producción usar hashing)
        return clienteRepository.findByEmail(email)
                .filter(c -> c.getPassword().equals(password))
                .map(c -> Map.of("token", JwtUtil.generarToken(c.getEmail())))
                .orElse(Map.of("error", "Credenciales inválidas"));
    }
}