package com.example.appbienestar.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbienestar.data.Cliente
import com.example.appbienestar.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClienteViewModel : ViewModel() {
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    fun cargarClientes() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitInstance.api.listarClientes()
                if (respuesta.isSuccessful) {
                    _clientes.value = respuesta.body() ?: emptyList()
                } else {
                    _mensaje.value = "Error al obtener clientes"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }
}