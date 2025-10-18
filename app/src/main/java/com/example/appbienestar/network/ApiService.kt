package com.example.appbienestar.network
import com.example.appbienestar.data.Cliente
import retrofit2.Response
import retrofit2.http.*
interface ApiService {
    @GET("clientes")
    suspend fun listarClientes(): Response<List<Cliente>>

    @POST("clientes")
    suspend fun agregarCliente(@Body cliente: Cliente): Response<String>

    @DELETE("clientes/{id}")
    suspend fun eliminarCliente(@Path("id") id: Int): Response<String>
}
