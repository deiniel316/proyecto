package com.example.appbienestar.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appbienestar.data.Servicio
import com.example.appbienestar.viewModel.ServiciosViewModel

@Composable
fun ServiciosScreen(viewModel: ServiciosViewModel = viewModel()) {
    val servicios by viewModel.servicios.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarServicios()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("💼 Lista de Servicios", style = MaterialTheme.typography.headlineMedium)

        if (mensaje.isNotEmpty()) {
            Text(text = mensaje, color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
            items(servicios) { s ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("🧾 ${s.nombre}", style = MaterialTheme.typography.bodyLarge)
                        Text("💲 Precio: ${s.precio}")
                        Text("📋 ${s.descripcion}")
                    }
                }
            }
        }
    }
}