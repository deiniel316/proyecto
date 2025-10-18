package com.example.appbienestar.data

data class Cliente(val id: Int? = null,
                   val nombreCompleto: String,
                   val dpi: String,
                   val email: String? = null,
                   val password: String? = null,
                   val telefono: String? = null,
                   val fechaNacimiento: String? = null
)
