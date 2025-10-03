/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bienestar.backend;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificacionService {
    private final List<Observer> observers = new ArrayList<>();

    // permite registrar observadores (se pueden inyectar desde @Configuration o @PostConstruct)
    public void addObserver(Observer o) { observers.add(o); }

    public void notifyAllObservers(String mensaje) {
        for (Observer o : observers) {
            o.update(mensaje);
        }
    }
}