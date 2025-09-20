package com.bienestar.backend;


import java.util.List;
import java.util.regex.Pattern;

public class ClienteService {

    private final ClienteRepository repository = new ClienteRepository();

    // Expresión regular para email válido
    private static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    // Validación de contraseña (min 8 caracteres, al menos 1 letra y 1 número)
    private static final Pattern PASSWORD_REGEX =
            Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    public String registrarCliente(Cliente cliente) {
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().isEmpty()) {
            return "Nombre es obligatorio.";
        }
        if (cliente.getDpi() == null || cliente.getDpi().length() < 8) {
            return "DPI debe tener al menos 8 dígitos.";
        }
        if (repository.existsByDni(cliente.getDpi())) {
            return "DPI ya registrado.";
        }
        if (cliente.getEmail() == null || !EMAIL_REGEX.matcher(cliente.getEmail()).matches()) {
            return "Email inválido.";
        }
        if (repository.existsByEmail(cliente.getEmail())) {
            return "Email ya registrado.";
        }
        if (cliente.getPassword() == null || !PASSWORD_REGEX.matcher(cliente.getPassword()).matches()) {
            return "La contraseña debe tener al menos 8 caracteres, incluir letra y número.";
        }

        boolean guardado = repository.save(cliente);
        return guardado ? "Cliente registrado con éxito." : "ID duplicado.";
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente buscarPorId(int id) {
        return repository.findById(id);
    }

    public String actualizar(int id, Cliente cliente) {
        if (!repository.update(id, cliente)) {
            return "Cliente no encontrado.";
        }
        return "Cliente actualizado.";
    }

    public String eliminar(int id) {
        if (!repository.delete(id)) {
            return "Cliente no encontrado.";
        }
        return "Cliente eliminado.";
    }
}