package com.example.appbienestar.network

import com.example.appbienestar.data.Cita
import com.example.appbienestar.data.Cliente
import com.example.appbienestar.data.LoginResponse
import com.example.appbienestar.data.Servicio
import com.example.appbienestar.data.Usuario
import retrofit2.Response
import retrofit2.http.*
interface ApiService {
    @GET("clientes")
    suspend fun listarClientes(): Response<List<Cliente>>

    @GET("servicios")
    suspend fun listarServicios(): Response<List<Servicio>>

    @POST("clientes")
    suspend fun agregarCliente(@Body cliente: Cliente): Response<String>

    @DELETE("clientes/{id}")
    suspend fun eliminarCliente(@Path("id") id: Int): Response<String>
   @POST("auth/login")
   suspend fun login(@Body usuario: Usuario): Response<LoginResponse>
    @GET("/citas")
    suspend fun listarCitas(): Response<List<Cita>>

    @POST("/citas")
    suspend fun agendarCita(@Body cita: Cita): Response<Cita>

    @PUT("/citas/{id}/cancelar")
    suspend fun cancelarCita(@Path("id") id: Int): Response<String>
}
