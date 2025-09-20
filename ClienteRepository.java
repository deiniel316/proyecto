package com.bienestar.backend;

import java.util.*;

public class ClienteRepository {

    private final Map<Integer, Cliente> clientes = new HashMap<>();

    public List<Cliente> findAll() {
        return new ArrayList<>(clientes.values());
    }

    public Cliente findById(int id) {
        return clientes.get(id);
    }

    public boolean save(Cliente cliente) {
        // Validación: ID único
        if (clientes.containsKey(cliente.getId())) {
            return false;
        }
        clientes.put(cliente.getId(), cliente);
        return true;
    }

    public boolean update(int id, Cliente cliente) {
        if (!clientes.containsKey(id)) {
            return false;
        }
        clientes.put(id, cliente);
        return true;
    }

    public boolean delete(int id) {
        return clientes.remove(id) != null;
    }

    // Validación de unicidad
    public boolean existsByEmail(String email) {
        return clientes.values().stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }

    public boolean existsByDni(String dpi) {
        return clientes.values().stream()
                .anyMatch(c -> c.getDpi().equalsIgnoreCase(dpi));
    }
}