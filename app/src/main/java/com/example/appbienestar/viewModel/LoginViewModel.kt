package com.example.appbienestar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appbienestar.data.Usuario
import com.example.appbienestar.data.LoginResponse
import com.example.appbienestar.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun login(username: String, password: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response: Response<LoginResponse> =
                    RetrofitInstance.api.login(Usuario(username, password))

                if (response.isSuccessful) {
                    val token = response.body()?.token ?: ""
                    onSuccess(token)
                    _mensaje.value = "✅ Bienvenido $username"
                } else {
                    _mensaje.value = "❌ Credenciales incorrectas"
                }
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }
}