package com.example.appbienestar.viewModel

import com.example.appbienestar.data.Cita
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbienestar.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CitaViewModel : ViewModel() {

    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun cargarCitas() {
        viewModelScope.launch {
            try {
                val respuesta = RetrofitInstance.api.listarCitas()
                if (respuesta.isSuccessful) {
                    _citas.value = respuesta.body() ?: emptyList()
                }
            } catch (e: Exception) {
                _mensaje.value = " Error: ${e.message}"
            }
        }
    }

    fun agendarCita(cita: Cita) {
        viewModelScope.launch {
            try {
                val res = RetrofitInstance.api.agendarCita(cita)
                if (res.isSuccessful) {
                    _mensaje.value = " Cita agendada correctamente"
                    cargarCitas()
                }
            } catch (e: Exception) {
                _mensaje.value = " Error al agendar cita"
            }
        }
    }

    fun cancelarCita(id: Int) {
        viewModelScope.launch {
            try {
                val res = RetrofitInstance.api.cancelarCita(id)
                if (res.isSuccessful) {
                    _mensaje.value = " Cita cancelada"
                    cargarCitas()
                }
            } catch (e: Exception) {
                _mensaje.value = " Error al cancelar cita"
            }
        }
    }
}