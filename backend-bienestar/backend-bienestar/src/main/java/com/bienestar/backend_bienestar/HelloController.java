/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.bienestar.backend_bienestar;

/**
 *
 * @author danie
 */
public class HelloController {

    package com.bienestar.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String hello() {
        return "Backend Bienestar funcionando 🚀";
    }
}
}
