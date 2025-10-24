package com.example.appbienestar.data

data class Cita(
    val id: Int? = null,
    val cliente: String,
    val servicio: String,
    val fechaHora: String,
    val estado: String
)