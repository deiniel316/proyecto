package com.bienestar.backend;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    public String registrarCliente(Cliente cliente) {
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().isEmpty()) {
            return "❌ Nombre es obligatorio.";
        }
        if (cliente.getDpi() == null || cliente.getDpi().length() < 8) {
            return "❌ DNI debe tener al menos 8 dígitos.";
        }
        if (repository.existsByDni(cliente.getDpi())) {
            return "⚠️ DNI ya registrado.";
        }
        if (cliente.getEmail() == null || !EMAIL_REGEX.matcher(cliente.getEmail()).matches()) {
            return "❌ Email inválido.";
        }
        if (repository.existsByEmail(cliente.getEmail())) {
            return "⚠️ Email ya registrado.";
        }
        if (cliente.getPassword() == null || !PASSWORD_REGEX.matcher(cliente.getPassword()).matches()) {
            return "❌ La contraseña debe tener al menos 8 caracteres, incluir letra y número.";
        }

        repository.save(cliente);
        return "✅ Cliente registrado con éxito.";
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public String actualizar(Integer id, Cliente cliente) {
        if (!repository.existsById(id)) {
            return "⚠️ Cliente no encontrado.";
        }
        cliente.setId(id);
        repository.save(cliente);
        return "✅ Cliente actualizado.";
    }

    public String eliminar(Integer id) {
        if (!repository.existsById(id)) {
            return "⚠️ Cliente no encontrado.";
        }
        repository.deleteById(id);
        return "✅ Cliente eliminado.";
    }
}