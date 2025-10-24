package com.example.appbienestar.data

data class Usuario(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val message: String
)