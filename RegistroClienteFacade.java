/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bienestar.backend;

import org.springframework.stereotype.Component;

@Component
public class RegistroClienteFacade {

    private final ClienteService clienteService;
    private final NotificacionService notificacionService;
    private final AuditoriaRepository auditoriaRepository;

    public RegistroClienteFacade(ClienteService ClienteService,
                                 NotificacionService notificacionService,
                                 AuditoriaRepository auditoriaRepository) {
        this.clienteService = ClienteService;
        this.notificacionService = notificacionService;
        this.auditoriaRepository = auditoriaRepository;
    }

    /**
     * Registra cliente, notifica y crea registro de auditoría.
     * Devuelve el mensaje resultante de la operación.
     * @param cliente
     * @param usuarioQueRealiza
     * @return 
     */
    
    public String registrarCliente(Cliente cliente, String usuarioQueRealiza) {
        String resultado = clienteService.registrarCliente(cliente);
        if (resultado.startsWith("ok")) {
            // notificar
            notificacionService.notifyAllObservers("Nuevo cliente: " + cliente.getNombreCompleto());
            // auditar
            auditoriaRepository.save(new Auditoria("Registro de cliente", usuarioQueRealiza));
        }
        return resultado;
    }
}