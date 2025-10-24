package com.example.appbienestar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbienestar.data.Servicio
import com.example.appbienestar.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ServiciosViewModel : ViewModel() {

    private val _servicios = MutableStateFlow<List<Servicio>>(emptyList())
    val servicios: StateFlow<List<Servicio>> = _servicios

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun cargarServicios() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitInstance.api.listarServicios()
                if (respuesta.isSuccessful) {
                    _servicios.value = respuesta.body() ?: emptyList()
                } else {
                    _mensaje.value = "Error al obtener servicios"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }
}
