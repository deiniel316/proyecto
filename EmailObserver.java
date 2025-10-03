/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bienestar.backend;
import org.springframework.stereotype.Component;

@Component
public class EmailObserver implements Observer {
    @Override
    public void update(String mensaje) {
       
        System.out.println("EmailObserver: enviando correo -> " + mensaje);
    }
}