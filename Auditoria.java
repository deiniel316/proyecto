/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bienestar.backend;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Auditoria")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accion;
    private String usuario;
    private LocalDateTime fechaHora;

    public Auditoria() {}

    public Auditoria(String accion, String usuario) {
        this.accion = accion;
        this.usuario = usuario;
        this.fechaHora = LocalDateTime.now();
    }

    // getters y setters...
}