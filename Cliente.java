package com.bienestar.backend;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name= "cliente")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                 // ID interno generado
    
    @Column(nullable = false)
    private String nombreCompleto;
    
    @Column(unique = true, nullable = false, length = 20)
    private String dpi;             // Identificador único (mínimo 8 dígitos)
    
    private LocalDate fechaNacimiento;
    private String telefono;
    
    @Column(unique = true, nullable = false)
    private String email;           // Identificador único en App
    
    @Column(nullable = false)
    private String password;        // Solo App

    public Cliente(int id, String nombreCompleto, String dpi,
                   LocalDate fechaNacimiento, String telefono,
                   String email, String password) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.dpi = dpi;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    /**
     *
     * @return
     */
    public String getDpi() { return dpi; }
    public void setDpi(String dpi) { this.dpi = dpi; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDpiString() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
