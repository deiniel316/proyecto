package com.bienestar.backend;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class NotificacionConfig {
    @Autowired private NotificacionService notificacionService;
    @Autowired private EmailObserver emailObserver;

    @PostConstruct
    public void init() {
        notificacionService.addObserver(emailObserver);
    }
}